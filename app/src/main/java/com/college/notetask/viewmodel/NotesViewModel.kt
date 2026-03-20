package com.college.notetask.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.college.notetask.domain.model.Note
import com.college.notetask.domain.usecase.DeleteNoteUseCase
import com.college.notetask.domain.usecase.GetNoteByIdUseCase
import com.college.notetask.domain.usecase.SaveNoteUseCase
import com.college.notetask.domain.usecase.SearchNotesUseCase
import com.college.notetask.domain.usecase.TogglePinNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

// ─────────────────────────────────────────────────────────────────────────────
// UI State
// ─────────────────────────────────────────────────────────────────────────────

/**
 * NotesUiState
 *
 * A single immutable snapshot of everything the Notes screens need to render.
 * Using a single state class (instead of many StateFlows) avoids race conditions
 * where the UI sees inconsistent combinations of state.
 */
data class NotesUiState(
    val notes: List<Note>      = emptyList(),
    val searchQuery: String    = "",
    val isLoading: Boolean     = true,
    val errorMessage: String?  = null,
    val successMessage: String? = null
)

// ─────────────────────────────────────────────────────────────────────────────
// ViewModel
// ─────────────────────────────────────────────────────────────────────────────

/**
 * NotesViewModel
 *
 * Sits between the UI (Composables) and the domain layer (use cases).
 * The UI only reads [uiState] and calls public fun — it never touches Room directly.
 *
 * Flow of data:
 *   searchQuery changes → debounce 300ms → SearchNotesUseCase → Room query
 *   → list emitted → _uiState updated → UI recomposes
 */
@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class NotesViewModel @Inject constructor(
    private val searchNotes   : SearchNotesUseCase,
    private val getNoteById   : GetNoteByIdUseCase,
    private val saveNote      : SaveNoteUseCase,
    private val deleteNote    : DeleteNoteUseCase,
    private val togglePin     : TogglePinNoteUseCase
) : ViewModel() {

    // ── Exposed state ──────────────────────────────────────────
    private val _uiState = MutableStateFlow(NotesUiState())
    val uiState: StateFlow<NotesUiState> = _uiState.asStateFlow()

    // The note being viewed on the Add/Edit screen (null = new note)
    private val _currentNote = MutableStateFlow<Note?>(null)
    val currentNote: StateFlow<Note?> = _currentNote.asStateFlow()

    // Internal search query driver
    private val _searchQuery = MutableStateFlow("")

    // ── Initialise list observation ───────────────────────────
    init {
        viewModelScope.launch {
            _searchQuery
                .debounce(300L)                         // wait for user to stop typing
                .flatMapLatest { query -> searchNotes(query) }
                .catch { e ->
                    _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
                }
                .collect { notes ->
                    _uiState.update { it.copy(notes = notes, isLoading = false) }
                }
        }
    }

    // ── Public API ─────────────────────────────────────────────

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        _uiState.update { it.copy(searchQuery = query) }
    }

    /**
     * Called when the Add/Edit screen opens.
     * Loads the note from DB if [noteId] is provided; clears otherwise.
     */
    fun loadNoteForEdit(noteId: Int?) {
        _currentNote.value = null
        if (noteId == null) return

        viewModelScope.launch {
            getNoteById(noteId).collect { note ->
                _currentNote.value = note
            }
        }
    }

    /**
     * Creates or updates a note.
     * [SaveNoteUseCase] validates that it is not empty.
     */
    fun saveNote(title: String, content: String) {
        viewModelScope.launch {
            val base = _currentNote.value
            val noteToSave = if (base != null) {
                // Editing existing note — preserve id and createdAt
                base.copy(title = title, content = content, updatedAt = System.currentTimeMillis())
            } else {
                // Brand-new note
                Note(title = title, content = content)
            }

            saveNote(noteToSave).fold(
                onSuccess = {
                    _uiState.update { it.copy(successMessage = "Note saved!") }
                    _currentNote.value = null
                },
                onFailure = { e ->
                    _uiState.update { it.copy(errorMessage = e.message) }
                }
            )
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            deleteNote.invoke(note)
            _uiState.update { it.copy(successMessage = "Note deleted") }
        }
    }

    fun togglePin(noteId: Int, currentPinState: Boolean) {
        viewModelScope.launch {
            togglePin.invoke(noteId, !currentPinState)   // flip the pin
        }
    }

    fun clearMessages() {
        _uiState.update { it.copy(errorMessage = null, successMessage = null) }
    }
}
