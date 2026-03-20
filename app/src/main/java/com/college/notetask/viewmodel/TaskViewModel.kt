package com.college.notetask.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.college.notetask.domain.model.Priority
import com.college.notetask.domain.model.Task
import com.college.notetask.domain.model.TaskFilter
import com.college.notetask.domain.model.TaskStatus
import com.college.notetask.domain.usecase.DeleteTaskUseCase
import com.college.notetask.domain.usecase.GetAllTasksUseCase
import com.college.notetask.domain.usecase.GetTaskByIdUseCase
import com.college.notetask.domain.usecase.SaveTaskUseCase
import com.college.notetask.domain.usecase.ToggleTaskStatusUseCase
import com.college.notetask.utils.ReminderScheduler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

// ─────────────────────────────────────────────────────────────────────────────
// UI State
// ─────────────────────────────────────────────────────────────────────────────

data class TaskUiState(
    val tasks: List<Task>          = emptyList(),
    val activeFilter: TaskFilter   = TaskFilter(),
    val isLoading: Boolean         = true,
    val errorMessage: String?      = null,
    val successMessage: String?    = null
)

// ─────────────────────────────────────────────────────────────────────────────
// ViewModel
// ─────────────────────────────────────────────────────────────────────────────

/**
 * TaskViewModel
 *
 * Manages the Task list screen and Add/Edit task screen.
 * Reactively re-queries whenever the active filter changes.
 */
@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class TaskViewModel @Inject constructor(
    private val getAllTasks     : GetAllTasksUseCase,
    private val getTaskById    : GetTaskByIdUseCase,
    private val saveTask       : SaveTaskUseCase,
    private val deleteTask     : DeleteTaskUseCase,
    private val toggleStatus   : ToggleTaskStatusUseCase,
    private val reminderScheduler: ReminderScheduler
) : ViewModel() {

    private val _uiState = MutableStateFlow(TaskUiState())
    val uiState: StateFlow<TaskUiState> = _uiState.asStateFlow()

    private val _activeFilter = MutableStateFlow(TaskFilter())

    private val _currentTask = MutableStateFlow<Task?>(null)
    val currentTask: StateFlow<Task?> = _currentTask.asStateFlow()

    init {
        viewModelScope.launch {
            _activeFilter
                .flatMapLatest { filter -> getAllTasks(filter) }
                .catch { e -> _uiState.update { it.copy(isLoading = false, errorMessage = e.message) } }
                .collect { tasks ->
                    _uiState.update { it.copy(tasks = tasks, isLoading = false) }
                }
        }
    }

    // ── Filter ─────────────────────────────────────────────────

    fun applyFilter(filter: TaskFilter) {
        _activeFilter.value = filter
        _uiState.update { it.copy(activeFilter = filter) }
    }

    fun clearFilter() = applyFilter(TaskFilter())

    // ── Add / Edit ─────────────────────────────────────────────

    fun loadTaskForEdit(taskId: Int?) {
        _currentTask.value = null
        if (taskId == null) return

        viewModelScope.launch {
            getTaskById(taskId).collect { task ->
                _currentTask.value = task
            }
        }
    }

    fun saveTask(
        title: String,
        description: String,
        dueDate: Long?,
        priority: Priority,
        reminderAt: Long?
    ) {
        viewModelScope.launch {
            val base = _currentTask.value
            val taskToSave = if (base != null) {
                base.copy(
                    title       = title,
                    description = description,
                    dueDate     = dueDate,
                    priority    = priority,
                    reminderAt  = reminderAt,
                    updatedAt   = System.currentTimeMillis()
                )
            } else {
                Task(
                    title       = title,
                    description = description,
                    dueDate     = dueDate,
                    priority    = priority,
                    reminderAt  = reminderAt
                )
            }

            saveTask(taskToSave).fold(
                onSuccess = { taskId ->
                    // Cancel old reminder, schedule new one if set
                    reminderScheduler.cancelReminder(taskId.toInt())
                    reminderAt?.let {
                        reminderScheduler.scheduleReminder(
                            taskId       = taskId.toInt(),
                            taskTitle    = title,
                            reminderTime = it
                        )
                    }
                    _uiState.update { it.copy(successMessage = "Task saved!") }
                    _currentTask.value = null
                },
                onFailure = { e ->
                    _uiState.update { it.copy(errorMessage = e.message) }
                }
            )
        }
    }

    // ── Status toggle ──────────────────────────────────────────

    fun toggleStatus(task: Task) {
        viewModelScope.launch {
            toggleStatus(task.id, task.status)
            // Cancel reminder if task is completed
            if (task.status == TaskStatus.PENDING) {
                reminderScheduler.cancelReminder(task.id)
            }
        }
    }

    // ── Delete ─────────────────────────────────────────────────

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            reminderScheduler.cancelReminder(task.id)
            deleteTask.invoke(task)
            _uiState.update { it.copy(successMessage = "Task deleted") }
        }
    }

    fun clearMessages() {
        _uiState.update { it.copy(errorMessage = null, successMessage = null) }
    }
}
