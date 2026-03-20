package com.college.notetask.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.college.notetask.ui.screens.notes.AddEditNoteScreen
import com.college.notetask.ui.screens.notes.NotesListScreen
import com.college.notetask.ui.screens.tasks.AddEditTaskScreen
import com.college.notetask.ui.screens.tasks.TaskListScreen

// ─────────────────────────────────────────────────────────────────────────────
// Route constants
// ─────────────────────────────────────────────────────────────────────────────

/**
 * Routes
 *
 * All navigation route strings live here — never hardcode them in Composables.
 * Helper functions build the route with optional arguments.
 */
object Routes {
    const val NOTES_LIST    = "notes_list"
    const val ADD_EDIT_NOTE = "add_edit_note?noteId={noteId}"
    const val TASK_LIST     = "task_list"
    const val ADD_EDIT_TASK = "add_edit_task?taskId={taskId}"

    /** Pass -1 to indicate "new note" */
    fun addEditNote(noteId: Int = -1) = "add_edit_note?noteId=$noteId"

    /** Pass -1 to indicate "new task" */
    fun addEditTask(taskId: Int = -1) = "add_edit_task?taskId=$taskId"
}

// ─────────────────────────────────────────────────────────────────────────────
// NavHost
// ─────────────────────────────────────────────────────────────────────────────

/**
 * NoteTaskNavHost
 *
 * The single navigation graph for the entire app.
 * Called once from MainActivity — all screen transitions happen here.
 *
 * Screen flow:
 *   NotesListScreen  ←→  AddEditNoteScreen
 *        ↓
 *   TaskListScreen   ←→  AddEditTaskScreen
 */
@Composable
fun NoteTaskNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController  = navController,
        startDestination = Routes.NOTES_LIST
    ) {

        // ── Notes List ──────────────────────────────────────────
        composable(Routes.NOTES_LIST) {
            NotesListScreen(
                onAddNote        = { navController.navigate(Routes.addEditNote()) },
                onEditNote       = { id -> navController.navigate(Routes.addEditNote(id)) },
                onNavigateToTasks = { navController.navigate(Routes.TASK_LIST) }
            )
        }

        // ── Add / Edit Note ─────────────────────────────────────
        composable(
            route     = Routes.ADD_EDIT_NOTE,
            arguments = listOf(
                navArgument("noteId") {
                    type         = NavType.IntType
                    defaultValue = -1           // -1 = new note
                }
            )
        ) { backStack ->
            val rawId  = backStack.arguments?.getInt("noteId") ?: -1
            val noteId = if (rawId == -1) null else rawId

            AddEditNoteScreen(
                noteId          = noteId,
                onNavigateBack  = { navController.popBackStack() }
            )
        }

        // ── Task List ───────────────────────────────────────────
        composable(Routes.TASK_LIST) {
            TaskListScreen(
                onAddTask         = { navController.navigate(Routes.addEditTask()) },
                onEditTask        = { id -> navController.navigate(Routes.addEditTask(id)) },
                onNavigateToNotes = { navController.popBackStack() }
            )
        }

        // ── Add / Edit Task ─────────────────────────────────────
        composable(
            route     = Routes.ADD_EDIT_TASK,
            arguments = listOf(
                navArgument("taskId") {
                    type         = NavType.IntType
                    defaultValue = -1
                }
            )
        ) { backStack ->
            val rawId  = backStack.arguments?.getInt("taskId") ?: -1
            val taskId = if (rawId == -1) null else rawId

            AddEditTaskScreen(
                taskId         = taskId,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
