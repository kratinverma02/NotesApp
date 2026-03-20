package com.college.notetask.ui.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/**
 * EmptyState
 *
 * A reusable "nothing here yet" placeholder used by both
 * NotesListScreen and TaskListScreen.
 *
 * The icon gently pulses using an infinite alpha animation
 * to draw the user's eye without being distracting.
 */
@Composable
fun EmptyState(
    icon: ImageVector,
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    // Infinite pulse animation on the icon
    val infiniteTransition = rememberInfiniteTransition(label = "empty_state_pulse")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue  = 0.9f,
        animationSpec = infiniteRepeatable(
            animation  = tween(durationMillis = 1_500),
            repeatMode = RepeatMode.Reverse
        ),
        label = "icon_alpha"
    )

    Box(
        modifier        = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment    = Alignment.CenterHorizontally,
            verticalArrangement    = Arrangement.spacedBy(12.dp),
            modifier               = Modifier.padding(horizontal = 40.dp)
        ) {
            Icon(
                imageVector     = icon,
                contentDescription = null,
                modifier        = Modifier
                    .size(96.dp)
                    .alpha(alpha),
                tint            = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
            )

            Text(
                text  = title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(Modifier.height(4.dp))

            Text(
                text      = subtitle,
                style     = MaterialTheme.typography.bodyMedium,
                color     = MaterialTheme.colorScheme.outline,
                textAlign = TextAlign.Center
            )
        }
    }
}
