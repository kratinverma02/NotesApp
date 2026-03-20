package com.college.notetask.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * NoteEntity
 *
 * This is a Room @Entity — it maps directly to a table called "notes" in SQLite.
 * Each property = a column in that table.
 *
 * @PrimaryKey(autoGenerate = true) → Room auto-generates a unique ID for each note.
 */
@Entity(tableName = "notes")
data class NoteEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val title: String,

    val content: String,                  // Supports basic rich text (stored as String)

    val isPinned: Boolean = false,         // Pin/Unpin feature

    val createdAt: Long = System.currentTimeMillis(),  // Epoch timestamp

    val updatedAt: Long = System.currentTimeMillis()   // Updated on edit
)
