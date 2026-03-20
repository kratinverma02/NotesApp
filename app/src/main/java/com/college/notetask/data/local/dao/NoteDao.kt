package com.college.notetask.data.local.dao

import androidx.room.*
import com.college.notetask.data.local.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

/**
 * NoteDao
 *
 * Data Access Object — defines all SQL operations for the "notes" table.
 * Room generates the implementation at compile time.
 *
 * All functions return Flow<> so the UI auto-updates whenever data changes.
 * suspend functions are for one-shot operations (insert/update/delete).
 */
@Dao
interface NoteDao {

    /**
     * Get all notes ordered by:
     * 1. Pinned notes first (isPinned DESC)
     * 2. Then by most recently updated
     */
    @Query("""
        SELECT * FROM notes 
        ORDER BY isPinned DESC, updatedAt DESC
    """)
    fun getAllNotes(): Flow<List<NoteEntity>>

    /**
     * Search notes by title or content (case-insensitive LIKE query)
     * The % wildcards allow partial matches
     */
    @Query("""
        SELECT * FROM notes 
        WHERE title LIKE '%' || :query || '%' 
           OR content LIKE '%' || :query || '%'
        ORDER BY isPinned DESC, updatedAt DESC
    """)
    fun searchNotes(query: String): Flow<List<NoteEntity>>

    /**
     * Get a single note by ID (for edit screen)
     */
    @Query("SELECT * FROM notes WHERE id = :id")
    fun getNoteById(id: Int): Flow<NoteEntity?>

    /**
     * Insert or replace a note
     * OnConflictStrategy.REPLACE → if same ID exists, it updates
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteEntity): Long

    /**
     * Update an existing note
     */
    @Update
    suspend fun updateNote(note: NoteEntity)

    /**
     * Delete a specific note
     */
    @Delete
    suspend fun deleteNote(note: NoteEntity)

    /**
     * Toggle pin status of a note
     */
    @Query("UPDATE notes SET isPinned = :isPinned, updatedAt = :updatedAt WHERE id = :id")
    suspend fun updatePinStatus(id: Int, isPinned: Boolean, updatedAt: Long = System.currentTimeMillis())
}
