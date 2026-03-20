package com.college.notetask.data.repository

import com.college.notetask.data.local.dao.TaskDao
import com.college.notetask.data.local.entity.TaskEntity
import com.college.notetask.domain.model.Priority
import com.college.notetask.domain.model.Task
import com.college.notetask.domain.model.TaskFilter
import com.college.notetask.domain.model.TaskStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton
import com.college.notetask.data.local.entity.Priority as EntityPriority
import com.college.notetask.data.local.entity.TaskStatus as EntityStatus

/**
 * TaskRepository Interface
 */
interface TaskRepository {
    fun getAllTasks(): Flow<List<Task>>
    fun getFilteredTasks(filter: TaskFilter): Flow<List<Task>>
    fun getTaskById(id: Int): Flow<Task?>
    suspend fun getPendingTasksWithReminders(): List<Task>
    suspend fun insertTask(task: Task): Long
    suspend fun updateTask(task: Task)
    suspend fun deleteTask(task: Task)
    suspend fun toggleTaskStatus(id: Int, status: TaskStatus)
}

/**
 * TaskRepositoryImpl
 */
@Singleton
class TaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao
) : TaskRepository {

    override fun getAllTasks(): Flow<List<Task>> =
        taskDao.getAllTasks().map { it.map { entity -> entity.toDomain() } }

    override fun getFilteredTasks(filter: TaskFilter): Flow<List<Task>> {
        val entityStatus = filter.status?.toEntity()
        val entityPriority = filter.priority?.toEntity()

        return when {
            entityStatus != null && entityPriority != null ->
                taskDao.getTasksByStatusAndPriority(entityStatus, entityPriority)
            entityStatus != null ->
                taskDao.getTasksByStatus(entityStatus)
            entityPriority != null ->
                taskDao.getTasksByPriority(entityPriority)
            else ->
                taskDao.getAllTasks()
        }.map { it.map { entity -> entity.toDomain() } }
    }

    override fun getTaskById(id: Int): Flow<Task?> =
        taskDao.getTaskById(id).map { it?.toDomain() }

    override suspend fun getPendingTasksWithReminders(): List<Task> =
        taskDao.getPendingTasksWithReminders().map { it.toDomain() }

    override suspend fun insertTask(task: Task): Long =
        taskDao.insertTask(task.toEntity())

    override suspend fun updateTask(task: Task) =
        taskDao.updateTask(task.toEntity())

    override suspend fun deleteTask(task: Task) =
        taskDao.deleteTask(task.toEntity())

    override suspend fun toggleTaskStatus(id: Int, status: TaskStatus) =
        taskDao.updateTaskStatus(id, status.toEntity())
}

// ──────────────────────────────────────────────
// Mapper Extension Functions
// ──────────────────────────────────────────────

fun TaskEntity.toDomain() = Task(
    id = id,
    title = title,
    description = description,
    dueDate = dueDate,
    priority = priority.toDomain(),
    status = status.toDomain(),
    reminderAt = reminderAt,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun Task.toEntity() = TaskEntity(
    id = id,
    title = title,
    description = description,
    dueDate = dueDate,
    priority = priority.toEntity(),
    status = status.toEntity(),
    reminderAt = reminderAt,
    createdAt = createdAt,
    updatedAt = updatedAt
)

// Enum mappers
fun EntityPriority.toDomain() = when (this) {
    EntityPriority.LOW -> Priority.LOW
    EntityPriority.MEDIUM -> Priority.MEDIUM
    EntityPriority.HIGH -> Priority.HIGH
}

fun Priority.toEntity() = when (this) {
    Priority.LOW -> EntityPriority.LOW
    Priority.MEDIUM -> EntityPriority.MEDIUM
    Priority.HIGH -> EntityPriority.HIGH
}

fun EntityStatus.toDomain() = when (this) {
    EntityStatus.PENDING -> TaskStatus.PENDING
    EntityStatus.COMPLETED -> TaskStatus.COMPLETED
}

fun TaskStatus.toEntity() = when (this) {
    TaskStatus.PENDING -> EntityStatus.PENDING
    TaskStatus.COMPLETED -> EntityStatus.COMPLETED
}
