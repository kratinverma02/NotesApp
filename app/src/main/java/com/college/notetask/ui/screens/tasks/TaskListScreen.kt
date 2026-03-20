package com.college.notetask.ui.screens.tasks

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.college.notetask.domain.model.Priority
import com.college.notetask.domain.model.TaskFilter
import com.college.notetask.domain.model.TaskStatus
import com.college.notetask.ui.components.EmptyState
import com.college.notetask.ui.components.TaskCard
import com.college.notetask.viewmodel.TaskViewModel
import kotlinx.coroutines.launch

/**
 * TaskListScreen
 *
 * Shows all tasks in a scrollable list.
 * Supports:
 * - Filter by status (Pending / Completed / All)
 * - Filter by priority (Low / Medium / High / All)
 * - Inline status toggle (checkbox)
 * - Swipe-to-reveal delete (via card's overflow menu)
 * - Badge on filter icon when any filter is active
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    onAddTask: () -> Unit,
    onEditTask: (Int) -> Unit,
    onNavigateToNotes: () -> Unit,
    viewModel: TaskViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var showFilterSheet by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    val isFiltered = uiState.activeFilter.status != null ||
            uiState.activeFilter.priority != null

    LaunchedEffect(uiState.successMessage, uiState.errorMessage) {
        val msg = uiState.successMessage ?: uiState.errorMessage
        if (msg != null) {
            snackbarHostState.showSnackbar(msg)
            viewModel.clearMessages()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Tasks", style = MaterialTheme.typography.headlineSmall)
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateToNotes) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back to Notes")
                    }
                },
                actions = {
                    // Filter button with badge when active
                    BadgedBox(
                        badge = { if (isFiltered) Badge() }
                    ) {
                        IconButton(onClick = { showFilterSheet = true }) {
                            Icon(Icons.Default.FilterList, "Filter tasks")
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onAddTask,
                icon    = { Icon(Icons.Default.Add, null) },
                text    = { Text("New Task") }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->

        // Active filter summary chip row
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (isFiltered) {
                ActiveFilterRow(
                    filter  = uiState.activeFilter,
                    onClear = { viewModel.clearFilter() }
                )
            }

            AnimatedContent(
                targetState = uiState.tasks.isEmpty() && !uiState.isLoading,
                label = "task_list_content"
            ) { isEmpty ->
                if (isEmpty) {
                    EmptyState(
                        icon     = Icons.Default.CheckCircleOutline,
                        title    = if (isFiltered) "No Matching Tasks" else "No Tasks Yet",
                        subtitle = if (isFiltered) "Try adjusting your filters"
                                   else "Tap \"New Task\" to add your first task"
                    )
                } else {
                    LazyColumn(
                        contentPadding    = PaddingValues(
                            start  = 16.dp,
                            end    = 16.dp,
                            top    = 8.dp,
                            bottom = 100.dp
                        ),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(uiState.tasks, key = { it.id }) { task ->
                            TaskCard(
                                task           = task,
                                onToggleStatus = { viewModel.toggleStatus(task) },
                                onClick        = { onEditTask(task.id) },
                                onDelete       = { viewModel.deleteTask(task) }
                            )
                        }
                    }
                }
            }
        }
    }

    // ── Filter bottom sheet ───────────────────────────────────
    if (showFilterSheet) {
        FilterBottomSheet(
            currentFilter  = uiState.activeFilter,
            sheetState     = sheetState,
            onApplyFilter  = { filter ->
                viewModel.applyFilter(filter)
                scope.launch { sheetState.hide() }.invokeOnCompletion {
                    showFilterSheet = false
                }
            },
            onDismiss = { showFilterSheet = false }
        )
    }
}

// ── Active filter summary ─────────────────────────────────────

@Composable
private fun ActiveFilterRow(filter: TaskFilter, onClear: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        filter.status?.let {
            FilterChip(
                selected = true,
                onClick  = onClear,
                label    = { Text(it.name) }
            )
        }
        filter.priority?.let {
            FilterChip(
                selected = true,
                onClick  = onClear,
                label    = { Text(it.name) }
            )
        }
    }
    HorizontalDivider()
}

// ── Filter bottom sheet composable ───────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FilterBottomSheet(
    currentFilter: TaskFilter,
    sheetState: androidx.compose.material3.SheetState,
    onApplyFilter: (TaskFilter) -> Unit,
    onDismiss: () -> Unit
) {
    var selectedStatus   by remember { mutableStateOf(currentFilter.status) }
    var selectedPriority by remember { mutableStateOf(currentFilter.priority) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState       = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            Text("Filter Tasks", style = MaterialTheme.typography.titleLarge)

            Spacer(Modifier.height(16.dp))

            // ── Status filter ─────────────────────────────────
            Text("Status", style = MaterialTheme.typography.labelLarge)
            Spacer(Modifier.height(6.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                // "All" chip
                FilterChip(
                    selected = selectedStatus == null,
                    onClick  = { selectedStatus = null },
                    label    = { Text("All") }
                )
                TaskStatus.entries.forEach { status ->
                    FilterChip(
                        selected = selectedStatus == status,
                        onClick  = {
                            selectedStatus = if (selectedStatus == status) null else status
                        },
                        label = { Text(status.name.lowercase().replaceFirstChar { it.uppercase() }) }
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            // ── Priority filter ───────────────────────────────
            Text("Priority", style = MaterialTheme.typography.labelLarge)
            Spacer(Modifier.height(6.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterChip(
                    selected = selectedPriority == null,
                    onClick  = { selectedPriority = null },
                    label    = { Text("All") }
                )
                Priority.entries.forEach { priority ->
                    FilterChip(
                        selected = selectedPriority == priority,
                        onClick  = {
                            selectedPriority = if (selectedPriority == priority) null else priority
                        },
                        label = { Text(priority.name.lowercase().replaceFirstChar { it.uppercase() }) }
                    )
                }
            }

            Spacer(Modifier.height(28.dp))

            // ── Action buttons ────────────────────────────────
            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick  = { onApplyFilter(TaskFilter()) },
                    modifier = Modifier.weight(1f)
                ) { Text("Clear All") }

                Button(
                    onClick  = {
                        onApplyFilter(TaskFilter(selectedStatus, selectedPriority))
                    },
                    modifier = Modifier.weight(1f)
                ) { Text("Apply") }
            }

            Spacer(Modifier.height(40.dp))  // safe area padding
        }
    }
}
