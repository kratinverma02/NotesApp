package com.college.notetask.data.repository

import com.college.notetask.data.local.dao.NoteDao
import com.college.notetask.data.local.entity.NoteEntity
import com.college.notetask.domain.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * NoteRepository Interface
 *
 * Defines WHAT the repository can do (abstract contract).
 * ViewModels depend on this interface, NOT the implementation.
 * This makes it easy to swap implementations (e.g., add cloud sync later).
 */
interface NoteRepository {
    fun getAllNotes(): Flow<List<Note>>
    fun searchNotes(query: String): Flow<List<Note>>
    fun getNoteById(id: Int): Flow<Note?>
    suspend fun insertNote(note: Note): Long
    suspend fun updateNote(note: Note)
    suspend fun deleteNote(note: Note)
    suspend fun togglePin(id: Int, isPinned: Boolean)
}

/**
 * NoteRepositoryImpl
 *
 * The actual implementation that talks to Room (via NoteDao).
 * It also maps between domain Note ↔ Room NoteEntity.
 *
 * @Singleton ensures only one instance is created (via Hilt)
 * @Inject constructor tells Hilt how to create this class
 */
@Singleton
class NoteRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao
) : NoteRepository {

    override fun getAllNotes(): Flow<List<Note>> =
        noteDao.getAllNotes().map { entities -> entities.map { it.toDomain() } }

    override fun searchNotes(query: String): Flow<List<Note>> =
        noteDao.searchNotes(query).map { entities -> entities.map { it.toDomain() } }

    override fun getNoteById(id: Int): Flow<Note?> =
        noteDao.getNoteById(id).map { it?.toDomain() }

    override suspend fun insertNote(note: Note): Long =
        noteDao.insertNote(note.toEntity())

    override suspend fun updateNote(note: Note) =
        noteDao.updateNote(note.toEntity())

    override suspend fun deleteNote(note: Note) =
        noteDao.deleteNote(note.toEntity())

    override suspend fun togglePin(id: Int, isPinned: Boolean) =
        noteDao.updatePinStatus(id, isPinned)
}

// ──────────────────────────────────────────────
// Mapper Extension Functions
// ──────────────────────────────────────────────

/** Convert Room entity → Domain model */
fun NoteEntity.toDomain() = Note(
    id = id,
    title = title,
    content = content,
    isPinned = isPinned,
    createdAt = createdAt,
    updatedAt = updatedAt
)

/** Convert Domain model → Room entity */
fun Note.toEntity() = NoteEntity(
    id = id,
    title = title,
    content = content,
    isPinned = isPinned,
    createdAt = createdAt,
    updatedAt = updatedAt
)
