package com.college.notetask.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.college.notetask.domain.model.Priority
import com.college.notetask.ui.theme.PriorityHigh
import com.college.notetask.ui.theme.PriorityLow
import com.college.notetask.ui.theme.PriorityMedium

// ─────────────────────────────────────────────────────────────────────────────
// Priority colour helper
// ─────────────────────────────────────────────────────────────────────────────

fun priorityColor(priority: Priority): Color = when (priority) {
    Priority.LOW    -> PriorityLow
    Priority.MEDIUM -> PriorityMedium
    Priority.HIGH   -> PriorityHigh
}

// ─────────────────────────────────────────────────────────────────────────────
// PriorityChip — used on the Add/Edit Task screen
// ─────────────────────────────────────────────────────────────────────────────

/**
 * A selectable chip for choosing task priority.
 * Shows a coloured dot next to the priority label.
 */
@Composable
fun PriorityChip(
    priority: Priority,
    selected: Boolean,
    onClick: () -> Unit
) {
    val color = priorityColor(priority)

    FilterChip(
        selected = selected,
        onClick  = onClick,
        label    = {
            Text(
                text  = priority.name.lowercase()
                    .replaceFirstChar { it.uppercase() },
                style = MaterialTheme.typography.labelMedium
            )
        },
        leadingIcon = {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(color)
            )
        },
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = color.copy(alpha = 0.18f),
            selectedLabelColor     = color,
            selectedLeadingIconColor = color
        )
    )
}

// ─────────────────────────────────────────────────────────────────────────────
// PriorityDot — compact indicator used inside TaskCard
// ─────────────────────────────────────────────────────────────────────────────

/**
 * A tiny 8dp coloured dot that represents priority at a glance.
 */
@Composable
fun PriorityDot(priority: Priority, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(8.dp)
            .clip(CircleShape)
            .background(priorityColor(priority))
    )
}

// ─────────────────────────────────────────────────────────────────────────────
// PriorityBadge — small labelled badge
// ─────────────────────────────────────────────────────────────────────────────

/**
 * An unselectable coloured label chip showing priority text.
 * Used as a read-only badge in task cards or detail views.
 */
@Composable
fun PriorityBadge(priority: Priority) {
    val color = priorityColor(priority)
    FilterChip(
        selected = true,
        onClick  = {},
        enabled  = false,
        label    = {
            Text(
                text  = priority.name,
                style = MaterialTheme.typography.labelSmall
            )
        },
        colors = FilterChipDefaults.filterChipColors(
            disabledSelectedContainerColor = color.copy(alpha = 0.15f),
            disabledLabelColor             = color
        )
    )
}
