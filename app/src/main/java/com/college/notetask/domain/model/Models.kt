package com.college.notetask.domain.model

/**
 * Domain Models
 *
 * These are the "pure" data models used in the UI and ViewModels.
 * They are SEPARATE from Room entities — this is Clean Architecture.
 *
 * Why separate? Because:
 * - Room entities are tied to database structure
 * - Domain models are tied to business logic
 * - If you switch DB or add an API, UI code doesn't change
 *
 * Mappers convert between Entity ↔ Domain Model (see repository).
 */

data class Note(
    val id: Int = 0,
    val title: String,
    val content: String,
    val isPinned: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

enum class Priority { LOW, MEDIUM, HIGH }

enum class TaskStatus { PENDING, COMPLETED }

data class Task(
    val id: Int = 0,
    val title: String,
    val description: String = "",
    val dueDate: Long? = null,
    val priority: Priority = Priority.MEDIUM,
    val status: TaskStatus = TaskStatus.PENDING,
    val reminderAt: Long? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

/**
 * Filter options for the Task list screen
 */
data class TaskFilter(
    val status: TaskStatus? = null,        // null = show all statuses
    val priority: Priority? = null         // null = show all priorities
)
