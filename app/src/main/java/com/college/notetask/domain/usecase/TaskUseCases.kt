package com.college.notetask.domain.usecase

import com.college.notetask.data.repository.TaskRepository
import com.college.notetask.domain.model.Task
import com.college.notetask.domain.model.TaskFilter
import com.college.notetask.domain.model.TaskStatus
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use Cases — Task Module
 */

class GetAllTasksUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    operator fun invoke(filter: TaskFilter = TaskFilter()): Flow<List<Task>> =
        repository.getFilteredTasks(filter)
}

class GetTaskByIdUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    operator fun invoke(id: Int): Flow<Task?> = repository.getTaskById(id)
}

class SaveTaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(task: Task): Result<Long> {
        if (task.title.isBlank()) {
            return Result.failure(Exception("Task title cannot be empty"))
        }

        return try {
            val taskToSave = task.copy(updatedAt = System.currentTimeMillis())
            val id = if (task.id == 0) {
                repository.insertTask(taskToSave)
            } else {
                repository.updateTask(taskToSave)
                task.id.toLong()
            }
            Result.success(id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

class DeleteTaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(task: Task) = repository.deleteTask(task)
}

class ToggleTaskStatusUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(id: Int, currentStatus: TaskStatus) {
        val newStatus = if (currentStatus == TaskStatus.PENDING)
            TaskStatus.COMPLETED else TaskStatus.PENDING
        repository.toggleTaskStatus(id, newStatus)
    }
}
