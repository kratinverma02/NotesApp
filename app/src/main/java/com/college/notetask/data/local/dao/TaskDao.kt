package com.college.notetask.data.local.dao

import androidx.room.*
import com.college.notetask.data.local.entity.Priority
import com.college.notetask.data.local.entity.TaskEntity
import com.college.notetask.data.local.entity.TaskStatus
import kotlinx.coroutines.flow.Flow

/**
 * TaskDao
 *
 * All SQL operations for the "tasks" table.
 * Priority and Status are stored as TEXT (via TypeConverter in AppDatabase).
 */
@Dao
interface TaskDao {

    /**
     * Get all tasks, pending first then completed, sorted by due date
     */
    @Query("""
        SELECT * FROM tasks 
        ORDER BY 
            CASE status WHEN 'PENDING' THEN 0 ELSE 1 END,
            CASE WHEN dueDate IS NULL THEN 1 ELSE 0 END,
            dueDate ASC
    """)
    fun getAllTasks(): Flow<List<TaskEntity>>

    /**
     * Filter tasks by status (PENDING or COMPLETED)
     */
    @Query("SELECT * FROM tasks WHERE status = :status ORDER BY dueDate ASC")
    fun getTasksByStatus(status: TaskStatus): Flow<List<TaskEntity>>

    /**
     * Filter tasks by priority (LOW, MEDIUM, HIGH)
     */
    @Query("SELECT * FROM tasks WHERE priority = :priority ORDER BY dueDate ASC")
    fun getTasksByPriority(priority: Priority): Flow<List<TaskEntity>>

    /**
     * Filter by both status and priority
     */
    @Query("""
        SELECT * FROM tasks 
        WHERE status = :status AND priority = :priority 
        ORDER BY dueDate ASC
    """)
    fun getTasksByStatusAndPriority(status: TaskStatus, priority: Priority): Flow<List<TaskEntity>>

    /**
     * Get a single task by ID
     */
    @Query("SELECT * FROM tasks WHERE id = :id")
    fun getTaskById(id: Int): Flow<TaskEntity?>

    /**
     * Get tasks with reminders that haven't been completed yet
     * Used by WorkManager to re-schedule reminders after reboot
     */
    @Query("""
        SELECT * FROM tasks 
        WHERE reminderAt IS NOT NULL 
          AND reminderAt > :currentTime 
          AND status = 'PENDING'
    """)
    suspend fun getPendingTasksWithReminders(currentTime: Long = System.currentTimeMillis()): List<TaskEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity): Long

    @Update
    suspend fun updateTask(task: TaskEntity)

    @Delete
    suspend fun deleteTask(task: TaskEntity)

    /**
     * Quick status toggle — mark task complete/incomplete
     */
    @Query("""
        UPDATE tasks 
        SET status = :status, updatedAt = :updatedAt 
        WHERE id = :id
    """)
    suspend fun updateTaskStatus(
        id: Int,
        status: TaskStatus,
        updatedAt: Long = System.currentTimeMillis()
    )
}
