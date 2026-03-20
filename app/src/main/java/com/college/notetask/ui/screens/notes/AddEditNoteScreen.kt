package com.college.notetask.ui.screens.notes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.college.notetask.ui.theme.PinnedYellow
import com.college.notetask.viewmodel.NotesViewModel

/**
 * AddEditNoteScreen
 *
 * Used for both creating a new note and editing an existing one.
 * When [noteId] is null → new note mode.
 * When [noteId] is an Int → edit mode, loads the note from the DB.
 *
 * The screen has a minimal, distraction-free layout:
 *   - Large title field at the top
 *   - Scrollable content field below
 *   - Save (✓) button and pin toggle in the top bar
 *
 * Auto-navigates back after a successful save.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditNoteScreen(
    noteId: Int?,
    onNavigateBack: () -> Unit,
    viewModel: NotesViewModel = hiltViewModel()
) {
    val uiState     by viewModel.uiState.collectAsState()
    val currentNote by viewModel.currentNote.collectAsState()

    // Load existing note when screen opens (null → new note)
    LaunchedEffect(noteId) {
        viewModel.loadNoteForEdit(noteId)
    }

    // Derive editable fields from the loaded note
    // `remember(currentNote)` re-initialises when the note arrives from DB
    var title   by remember(currentNote) { mutableStateOf(currentNote?.title   ?: "") }
    var content by remember(currentNote) { mutableStateOf(currentNote?.content ?: "") }

    // Auto-focus title field when creating a new note
    val titleFocusRequester = remember { FocusRequester() }
    LaunchedEffect(noteId) {
        if (noteId == null) titleFocusRequester.requestFocus()
    }

    // Navigate back after save succeeds
    LaunchedEffect(uiState.successMessage) {
        if (uiState.successMessage != null) {
            onNavigateBack()
        }
    }

    // Transparent text-field colours — keeps the editor feel clean
    val transparentColors = TextFieldDefaults.colors(
        focusedContainerColor       = Color.Transparent,
        unfocusedContainerColor     = Color.Transparent,
        disabledContainerColor      = Color.Transparent,
        focusedIndicatorColor       = Color.Transparent,
        unfocusedIndicatorColor     = Color.Transparent,
        disabledIndicatorColor      = Color.Transparent
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text  = if (noteId == null) "New Note" else "Edit Note",
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                },
                actions = {
                    // Pin toggle (only available when editing an existing note)
                    if (noteId != null) {
                        val isPinned = currentNote?.isPinned ?: false
                        IconButton(onClick = {
                            viewModel.togglePin(noteId, isPinned)
                        }) {
                            Icon(
                                Icons.Default.PushPin,
                                contentDescription = if (isPinned) "Unpin" else "Pin",
                                tint = if (isPinned) PinnedYellow
                                       else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    // Save button — disabled if both fields are blank
                    IconButton(
                        onClick  = { viewModel.saveNote(title, content) },
                        enabled  = title.isNotBlank() || content.isNotBlank()
                    ) {
                        Icon(
                            Icons.Default.Check,
                            contentDescription = "Save note",
                            tint = if (title.isNotBlank() || content.isNotBlank())
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.outline
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            // ── Error banner ──────────────────────────────────
            uiState.errorMessage?.let { error ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Text(
                        text     = error,
                        modifier = Modifier.padding(12.dp),
                        color    = MaterialTheme.colorScheme.onErrorContainer,
                        style    = MaterialTheme.typography.bodySmall
                    )
                }
            }

            // ── Title field ───────────────────────────────────
            TextField(
                value        = title,
                onValueChange = { title = it },
                modifier     = Modifier
                    .fillMaxWidth()
                    .focusRequester(titleFocusRequester),
                placeholder  = {
                    Text(
                        "Title",
                        style      = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.SemiBold,
                        color      = MaterialTheme.colorScheme.outline
                    )
                },
                textStyle    = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                colors       = transparentColors,
                singleLine   = false,
                maxLines     = 3
            )

            HorizontalDivider(
                modifier  = Modifier.padding(horizontal = 16.dp),
                thickness = 0.5.dp,
                color     = MaterialTheme.colorScheme.outlineVariant
            )

            Spacer(Modifier.height(4.dp))

            // ── Content field (fills remaining screen space) ──
            TextField(
                value         = content,
                onValueChange = { content = it },
                modifier      = Modifier
                    .fillMaxSize()
                    .weight(1f),
                placeholder   = {
                    Text(
                        "Start writing…",
                        color = MaterialTheme.colorScheme.outline
                    )
                },
                colors        = transparentColors
            )
        }
    }
}
