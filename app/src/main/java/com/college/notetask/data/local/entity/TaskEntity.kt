package com.college.notetask.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Priority levels for a Task
 * Stored as String in Room using @TypeConverter (see AppDatabase.kt)
 */
enum class Priority {
    LOW, MEDIUM, HIGH
}

/**
 * Task completion status
 */
enum class TaskStatus {
    PENDING, COMPLETED
}

/**
 * TaskEntity
 *
 * Room @Entity — maps to "tasks" table.
 * Includes all fields required by the project spec.
 *
 * Note: dueDate is stored as Long (epoch milliseconds) for easy sorting.
 * reminderAt is nullable — null means no reminder set.
 */
@Entity(tableName = "tasks")
data class TaskEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val title: String,

    val description: String = "",

    val dueDate: Long? = null,            // Nullable — task may not have a due date

    val priority: Priority = Priority.MEDIUM,

    val status: TaskStatus = TaskStatus.PENDING,

    val reminderAt: Long? = null,         // Epoch ms for reminder notification

    val createdAt: Long = System.currentTimeMillis(),

    val updatedAt: Long = System.currentTimeMillis()
)
