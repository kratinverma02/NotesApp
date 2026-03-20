package com.college.notetask.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.college.notetask.data.repository.TaskRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * BootReceiver
 *
 * WorkManager jobs are cancelled when the device reboots.
 * This BroadcastReceiver listens for ACTION_BOOT_COMPLETED and
 * re-schedules every pending task reminder from the database.
 *
 * Don't forget: declared in AndroidManifest.xml with BOOT_COMPLETED permission.
 */
@AndroidEntryPoint
class BootReceiver : BroadcastReceiver() {

    @Inject lateinit var taskRepository: TaskRepository
    @Inject lateinit var reminderScheduler: ReminderScheduler

    // BroadcastReceiver.onReceive must be synchronous,
    // so we use a fire-and-forget coroutine with SupervisorJob.
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != Intent.ACTION_BOOT_COMPLETED) return

        scope.launch {
            val tasks = taskRepository.getPendingTasksWithReminders()
            tasks.forEach { task ->
                task.reminderAt?.let { reminderTime ->
                    reminderScheduler.scheduleReminder(
                        taskId      = task.id,
                        taskTitle   = task.title,
                        reminderTime = reminderTime
                    )
                }
            }
        }
    }
}
