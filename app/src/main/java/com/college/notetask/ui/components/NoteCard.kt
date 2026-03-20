package com.college.notetask.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.college.notetask.domain.model.Note
import com.college.notetask.ui.theme.PinnedYellow
import com.college.notetask.utils.DateUtils

/**
 * NoteCard
 *
 * Displays a single note in the staggered grid.
 * - Single tap  → opens the note for editing
 * - Long press  → shows a context menu (Pin / Delete)
 *
 * Pinned notes get a warm secondary-container background and a
 * yellow push-pin icon; the colour transition is animated.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteCard(
    note: Note,
    onClick: () -> Unit,
    onPin: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showMenu by remember { mutableStateOf(false) }

    // Smoothly animate card colour when pin state changes
    val containerColor by animateColorAsState(
        targetValue = if (note.isPinned)
            MaterialTheme.colorScheme.secondaryContainer
        else
            MaterialTheme.colorScheme.surfaceVariant,
        animationSpec = tween(durationMillis = 300),
        label = "note_card_color"
    )

    Box(modifier = modifier) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .combinedClickable(
                    onClick     = onClick,
                    onLongClick = { showMenu = true }
                ),
            colors = CardDefaults.cardColors(containerColor = containerColor),
            elevation = CardDefaults.cardElevation(
                defaultElevation = if (note.isPinned) 4.dp else 1.dp
            )
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp)
            ) {
                // ── Header row: pin icon + title ──────────────
                Row(
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (note.isPinned) {
                        Icon(
                            imageVector        = Icons.Filled.PushPin,
                            contentDescription = "Pinned",
                            tint               = PinnedYellow,
                            modifier           = Modifier
                                .size(14.dp)
                                .padding(top = 2.dp)
                        )
                        Spacer(Modifier.width(4.dp))
                    }

                    Text(
                        text     = note.title.ifBlank { "Untitled" },
                        style    = MaterialTheme.typography.titleSmall,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                // ── Content preview ───────────────────────────
                if (note.content.isNotBlank()) {
                    Spacer(Modifier.height(6.dp))
                    Text(
                        text     = note.content,
                        style    = MaterialTheme.typography.bodySmall,
                        maxLines = 6,
                        overflow = TextOverflow.Ellipsis,
                        color    = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // ── Timestamp ─────────────────────────────────
                Spacer(Modifier.height(10.dp))
                Text(
                    text  = DateUtils.formatRelativeTime(note.updatedAt),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }

        // ── Long-press context menu ────────────────────────────
        DropdownMenu(
            expanded          = showMenu,
            onDismissRequest  = { showMenu = false }
        ) {
            DropdownMenuItem(
                text         = { Text(if (note.isPinned) "Unpin" else "Pin to top") },
                leadingIcon  = {
                    Icon(
                        Icons.Filled.PushPin,
                        contentDescription = null,
                        tint = if (note.isPinned) PinnedYellow
                               else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                onClick = { onPin(); showMenu = false }
            )
            DropdownMenuItem(
                text         = {
                    Text("Delete", color = MaterialTheme.colorScheme.error)
                },
                leadingIcon  = {
                    Icon(
                        Icons.Filled.Delete,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error
                    )
                },
                onClick = { onDelete(); showMenu = false }
            )
        }
    }
}
