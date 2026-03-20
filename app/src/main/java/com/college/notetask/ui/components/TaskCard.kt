package com.college.notetask.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.RadioButtonUnchecked
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.college.notetask.domain.model.Task
import com.college.notetask.domain.model.TaskStatus
import com.college.notetask.utils.DateUtils

/**
 * TaskCard
 *
 * Displays a single task row in the Task list.
 * - Checkbox button on the left toggles PENDING ↔ COMPLETED.
 * - Completed tasks get a strikethrough title and a muted background.
 * - Overflow menu (⋮) reveals Edit and Delete actions.
 * - Overdue tasks show the due date in error colour.
 */
@Composable
fun TaskCard(
    task: Task,
    onToggleStatus: () -> Unit,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isCompleted = task.status == TaskStatus.COMPLETED
    var showMenu by remember { mutableStateOf(false) }

    val containerColor by animateColorAsState(
        targetValue = if (isCompleted)
            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f)
        else
            MaterialTheme.colorScheme.surfaceVariant,
        animationSpec = tween(300),
        label = "task_card_bg"
    )

    Card(
        onClick  = onClick,
        modifier = modifier.fillMaxWidth(),
        colors   = CardDefaults.cardColors(containerColor = containerColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier          = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp, end = 8.dp, top = 10.dp, bottom = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // ── Checkbox ───────────────────────────────────────
            IconButton(
                onClick  = onToggleStatus,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector        = if (isCompleted)
                        Icons.Outlined.CheckCircle
                    else
                        Icons.Outlined.RadioButtonUnchecked,
                    contentDescription = "Toggle status",
                    tint               = if (isCompleted)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.outline,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(Modifier.width(4.dp))

            // ── Main content ───────────────────────────────────
            Column(modifier = Modifier.weight(1f)) {

                // Title
                Text(
                    text            = task.title,
                    style           = MaterialTheme.typography.bodyLarge,
                    maxLines        = 1,
                    overflow        = TextOverflow.Ellipsis,
                    textDecoration  = if (isCompleted) TextDecoration.LineThrough else null,
                    color           = if (isCompleted)
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.45f)
                    else
                        MaterialTheme.colorScheme.onSurface
                )

                // Description (one-line preview)
                if (task.description.isNotBlank()) {
                    Text(
                        text     = task.description,
                        style    = MaterialTheme.typography.bodySmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color    = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(Modifier.height(4.dp))

                // ── Metadata row: due date + priority + reminder ──
                Row(
                    verticalAlignment  = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Due date
                    task.dueDate?.let { dueMs ->
                        val overdue = !isCompleted && DateUtils.isOverdue(dueMs)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Filled.Event,
                                null,
                                modifier = Modifier.size(12.dp),
                                tint     = if (overdue)
                                    MaterialTheme.colorScheme.error
                                else
                                    MaterialTheme.colorScheme.outline
                            )
                            Spacer(Modifier.width(2.dp))
                            Text(
                                text  = DateUtils.formatDate(dueMs),
                                style = MaterialTheme.typography.labelSmall,
                                color = if (overdue)
                                    MaterialTheme.colorScheme.error
                                else
                                    MaterialTheme.colorScheme.outline
                            )
                        }
                    }

                    // Priority dot
                    PriorityDot(task.priority)

                    // Reminder bell (if set)
                    if (task.reminderAt != null && !isCompleted) {
                        Icon(
                            Icons.Filled.NotificationsActive,
                            contentDescription = "Reminder set",
                            modifier = Modifier.size(12.dp),
                            tint     = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            // ── Overflow menu ──────────────────────────────────
            Box {
                IconButton(onClick = { showMenu = true }) {
                    Icon(Icons.Filled.MoreVert, "More options")
                }
                DropdownMenu(
                    expanded         = showMenu,
                    onDismissRequest = { showMenu = false }
                ) {
                    DropdownMenuItem(
                        text        = { Text("Edit") },
                        leadingIcon = { Icon(Icons.Filled.Edit, null) },
                        onClick     = { onClick(); showMenu = false }
                    )
                    DropdownMenuItem(
                        text        = {
                            Text("Delete", color = MaterialTheme.colorScheme.error)
                        },
                        leadingIcon = {
                            Icon(
                                Icons.Filled.Delete,
                                null,
                                tint = MaterialTheme.colorScheme.error
                            )
                        },
                        onClick = { onDelete(); showMenu = false }
                    )
                }
            }
        }
    }
}
