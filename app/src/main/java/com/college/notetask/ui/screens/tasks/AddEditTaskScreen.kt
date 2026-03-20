package com.college.notetask.ui.screens.tasks

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Notes
import androidx.compose.material.icons.filled.Title
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.college.notetask.domain.model.Priority
import com.college.notetask.ui.components.PriorityChip
import com.college.notetask.utils.DateUtils
import com.college.notetask.viewmodel.TaskViewModel
import java.util.Calendar

/**
 * AddEditTaskScreen
 *
 * Used for both creating a new task and editing an existing one.
 * [taskId] == null → new task mode.
 * [taskId] is an Int → edit mode, pre-fills from DB.
 *
 * Fields:
 *   - Title (required)
 *   - Description (optional, multi-line)
 *   - Priority selector (Low / Medium / High chips)
 *   - Due date (native DatePickerDialog)
 *   - Reminder date+time (native DatePickerDialog + TimePickerDialog)
 *
 * On save success, auto-navigates back.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTaskScreen(
    taskId: Int?,
    onNavigateBack: () -> Unit,
    viewModel: TaskViewModel = hiltViewModel()
) {
    val uiState     by viewModel.uiState.collectAsState()
    val currentTask by viewModel.currentTask.collectAsState()
    val context     = LocalContext.current

    // Load task data when screen opens
    LaunchedEffect(taskId) {
        viewModel.loadTaskForEdit(taskId)
    }

    // Navigate back after successful save
    LaunchedEffect(uiState.successMessage) {
        if (uiState.successMessage != null) onNavigateBack()
    }

    // ── Editable field state ──────────────────────────────────
    // remember(currentTask) re-sets values when the task loads from DB
    var title       by remember(currentTask) { mutableStateOf(currentTask?.title       ?: "") }
    var description by remember(currentTask) { mutableStateOf(currentTask?.description ?: "") }
    var dueDate     by remember(currentTask) { mutableStateOf(currentTask?.dueDate) }
    var priority    by remember(currentTask) { mutableStateOf(currentTask?.priority    ?: Priority.MEDIUM) }
    var reminderAt  by remember(currentTask) { mutableStateOf(currentTask?.reminderAt) }

    // ── Native dialog helpers ─────────────────────────────────

    /** Opens a DatePickerDialog and calls [onDate] with the selected epoch ms. */
    fun showDatePicker(onDate: (Long) -> Unit) {
        val cal = Calendar.getInstance()
        DatePickerDialog(
            context,
            { _, year, month, day ->
                val selected = Calendar.getInstance().apply { set(year, month, day, 0, 0, 0) }
                selected.set(Calendar.MILLISECOND, 0)
                onDate(selected.timeInMillis)
            },
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    /** Opens a DatePickerDialog followed by a TimePickerDialog. */
    fun showDateTimePicker(onDateTime: (Long) -> Unit) {
        val cal = Calendar.getInstance()
        DatePickerDialog(
            context,
            { _, year, month, day ->
                TimePickerDialog(
                    context,
                    { _, hour, minute ->
                        val selected = Calendar.getInstance().apply {
                            set(year, month, day, hour, minute, 0)
                            set(Calendar.MILLISECOND, 0)
                        }
                        onDateTime(selected.timeInMillis)
                    },
                    cal.get(Calendar.HOUR_OF_DAY),
                    cal.get(Calendar.MINUTE),
                    false
                ).show()
            },
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    // ── UI ────────────────────────────────────────────────────
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text  = if (taskId == null) "New Task" else "Edit Task",
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                },
                actions = {
                    // Save — only active when title is filled
                    IconButton(
                        onClick  = {
                            viewModel.saveTask(title, description, dueDate, priority, reminderAt)
                        },
                        enabled = title.isNotBlank()
                    ) {
                        Icon(
                            Icons.Default.Check,
                            contentDescription = "Save task",
                            tint = if (title.isNotBlank())
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
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState()),   // scrollable form
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Spacer(Modifier.height(4.dp))

            // ── Error banner ──────────────────────────────────
            uiState.errorMessage?.let { error ->
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Text(
                        text     = error,
                        modifier = Modifier.padding(12.dp),
                        color    = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            }

            // ── Title ─────────────────────────────────────────
            OutlinedTextField(
                value         = title,
                onValueChange = { title = it },
                label         = { Text("Task title *") },
                modifier      = Modifier.fillMaxWidth(),
                leadingIcon   = { Icon(Icons.Default.Title, null) },
                isError       = title.isBlank(),
                supportingText = {
                    if (title.isBlank()) Text("Title is required")
                },
                singleLine = true
            )

            // ── Description ───────────────────────────────────
            OutlinedTextField(
                value         = description,
                onValueChange = { description = it },
                label         = { Text("Description (optional)") },
                modifier      = Modifier.fillMaxWidth(),
                leadingIcon   = { Icon(Icons.Default.Notes, null) },
                minLines      = 3,
                maxLines      = 6
            )

            // ── Priority ──────────────────────────────────────
            Column {
                Text(
                    "Priority",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.height(6.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Priority.entries.forEach { p ->
                        PriorityChip(
                            priority = p,
                            selected = priority == p,
                            onClick  = { priority = p }
                        )
                    }
                }
            }

            // ── Due Date ──────────────────────────────────────
            // OutlinedTextField is read-only; the clickable modifier
            // opens the native DatePickerDialog.
            OutlinedTextField(
                value         = dueDate?.let { DateUtils.formatDate(it) } ?: "",
                onValueChange = {},
                readOnly      = true,
                label         = { Text("Due date (optional)") },
                placeholder   = { Text("Tap to select") },
                modifier      = Modifier
                    .fillMaxWidth()
                    .clickable { showDatePicker { dueDate = it } },
                leadingIcon   = { Icon(Icons.Default.CalendarToday, null) },
                trailingIcon  = {
                    if (dueDate != null) {
                        IconButton(onClick = { dueDate = null }) {
                            Icon(Icons.Default.Clear, "Clear due date")
                        }
                    }
                },
                enabled = false,   // prevent keyboard from showing
                colors  = outlinedTextFieldDisabledColors()
            )

            // ── Reminder ──────────────────────────────────────
            OutlinedTextField(
                value         = reminderAt?.let { DateUtils.formatDateTime(it) } ?: "",
                onValueChange = {},
                readOnly      = true,
                label         = { Text("Reminder (optional)") },
                placeholder   = { Text("Tap to set reminder") },
                modifier      = Modifier
                    .fillMaxWidth()
                    .clickable { showDateTimePicker { reminderAt = it } },
                leadingIcon   = { Icon(Icons.Default.Alarm, null) },
                trailingIcon  = {
                    if (reminderAt != null) {
                        IconButton(onClick = { reminderAt = null }) {
                            Icon(Icons.Default.Clear, "Clear reminder")
                        }
                    }
                },
                enabled = false,
                colors  = outlinedTextFieldDisabledColors(),
                supportingText = {
                    if (reminderAt != null) {
                        Text(
                            "You will receive a notification at this time",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )

            // Bottom padding so content clears the keyboard
            Spacer(Modifier.height(80.dp))
        }
    }
}

/**
 * Returns OutlinedTextField colours that look "enabled" even when
 * enabled = false (so the disabled grey style doesn't confuse users
 * into thinking the field is broken).
 */
@Composable
private fun outlinedTextFieldDisabledColors() =
    androidx.compose.material3.OutlinedTextFieldDefaults.colors(
        disabledTextColor         = MaterialTheme.colorScheme.onSurface,
        disabledBorderColor       = MaterialTheme.colorScheme.outline,
        disabledLeadingIconColor  = MaterialTheme.colorScheme.onSurfaceVariant,
        disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
        disabledLabelColor        = MaterialTheme.colorScheme.onSurfaceVariant,
        disabledPlaceholderColor  = MaterialTheme.colorScheme.outline
    )
