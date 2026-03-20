package com.college.notetask.utils

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * ReminderScheduler
 *
 * Provides a clean API for scheduling / cancelling task reminders.
 * Internally delegates to WorkManager so reminders survive app restarts.
 *
 * Injected into TaskViewModel via Hilt.
 */
@Singleton
class ReminderScheduler @Inject constructor(
    @ApplicationContext private val context: Context
) {

    /**
     * Schedules a one-shot work request that fires at [reminderTime].
     * If a reminder for the same task already exists it is replaced.
     */
    fun scheduleReminder(taskId: Int, taskTitle: String, reminderTime: Long) {
        val delay = reminderTime - System.currentTimeMillis()
        if (delay <= 0) return          // Past time — skip silently

        val inputData = workDataOf(
            ReminderWorker.KEY_TASK_ID    to taskId,
            ReminderWorker.KEY_TASK_TITLE to taskTitle
        )

        val request = OneTimeWorkRequestBuilder<ReminderWorker>()
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .setInputData(inputData)
            .addTag("reminder_$taskId")
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            "reminder_$taskId",         // unique name — prevents duplicates
            ExistingWorkPolicy.REPLACE,
            request
        )
    }

    /** Cancels a previously scheduled reminder (e.g. on task deletion). */
    fun cancelReminder(taskId: Int) {
        WorkManager.getInstance(context).cancelUniqueWork("reminder_$taskId")
    }
}
