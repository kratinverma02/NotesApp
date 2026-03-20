package com.college.notetask.domain.usecase

import com.college.notetask.data.repository.NoteRepository
import com.college.notetask.domain.model.Note
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use Cases — Note Module
 *
 * Each use case represents ONE business action.
 * They wrap repository calls and can add validation/business logic.
 *
 * Why use cases?
 * - Keep ViewModels thin and focused on UI state
 * - Reusable business logic across multiple ViewModels
 * - Easy to unit test independently
 */

class GetAllNotesUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    operator fun invoke(): Flow<List<Note>> = repository.getAllNotes()
}

class SearchNotesUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    operator fun invoke(query: String): Flow<List<Note>> =
        if (query.isBlank()) repository.getAllNotes()
        else repository.searchNotes(query)
}

class GetNoteByIdUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    operator fun invoke(id: Int): Flow<Note?> = repository.getNoteById(id)
}

class SaveNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    /**
     * Validates note before saving.
     * If id == 0, it's a new note (insert). Otherwise, update.
     */
    suspend operator fun invoke(note: Note): Result<Unit> {
        if (note.title.isBlank() && note.content.isBlank()) {
            return Result.failure(Exception("Note cannot be empty"))
        }

        return try {
            val noteToSave = note.copy(updatedAt = System.currentTimeMillis())
            if (note.id == 0) {
                repository.insertNote(noteToSave)
            } else {
                repository.updateNote(noteToSave)
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

class DeleteNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(note: Note) = repository.deleteNote(note)
}

class TogglePinNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(id: Int, isPinned: Boolean) =
        repository.togglePin(id, isPinned)
}
