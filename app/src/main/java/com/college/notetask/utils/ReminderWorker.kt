package com.college.notetask.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

/**
 * ReminderWorker
 *
 * A WorkManager Worker that displays a local notification.
 * WorkManager calls doWork() at the scheduled delay time.
 *
 * @HiltWorker + @AssistedInject → Hilt can inject dependencies here.
 * The @Assisted parameters are always Context and WorkerParameters.
 */
@HiltWorker
class ReminderWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted params: WorkerParameters
) : CoroutineWorker(context, params) {

    companion object {
        const val KEY_TASK_ID    = "task_id"
        const val KEY_TASK_TITLE = "task_title"
        const val CHANNEL_ID     = "task_reminders"
        const val CHANNEL_NAME   = "Task Reminders"
    }

    override suspend fun doWork(): Result {
        val taskId    = inputData.getInt(KEY_TASK_ID, -1)
        val taskTitle = inputData.getString(KEY_TASK_TITLE) ?: "Task Reminder"

        createNotificationChannel()
        showNotification(taskId, taskTitle)

        return Result.success()
    }

    // Creates the notification channel (required on Android 8+)
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Reminders for your upcoming tasks"
                enableVibration(true)
            }
            val manager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    private fun showNotification(taskId: Int, title: String) {
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("⏰ Task Reminder")
            .setContentText(title)
            .setStyle(NotificationCompat.BigTextStyle().bigText("Don't forget: $title"))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        val manager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(taskId, notification)
    }
}
