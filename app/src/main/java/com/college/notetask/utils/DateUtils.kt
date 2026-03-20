package com.college.notetask.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

/**
 * DateUtils
 *
 * A singleton object that holds all date-formatting helpers.
 * Using `object` means we never need to instantiate this class —
 * just call DateUtils.formatDate(timestamp) anywhere.
 */
object DateUtils {

    private val dateFormat     = SimpleDateFormat("MMM d, yyyy",        Locale.getDefault())
    private val dateTimeFormat = SimpleDateFormat("MMM d, yyyy hh:mm a", Locale.getDefault())

    /** "Jan 15, 2025" */
    fun formatDate(timestamp: Long): String = dateFormat.format(Date(timestamp))

    /** "Jan 15, 2025 03:30 PM" */
    fun formatDateTime(timestamp: Long): String = dateTimeFormat.format(Date(timestamp))

    /**
     * "Just now" / "5m ago" / "2h ago" / "3d ago" / "Jan 15, 2025"
     * Used on note cards to show a friendly relative timestamp.
     */
    fun formatRelativeTime(timestamp: Long): String {
        val diff = System.currentTimeMillis() - timestamp
        return when {
            diff < TimeUnit.MINUTES.toMillis(1)  -> "Just now"
            diff < TimeUnit.HOURS.toMillis(1)    ->
                "${TimeUnit.MILLISECONDS.toMinutes(diff)}m ago"
            diff < TimeUnit.DAYS.toMillis(1)     ->
                "${TimeUnit.MILLISECONDS.toHours(diff)}h ago"
            diff < TimeUnit.DAYS.toMillis(7)     ->
                "${TimeUnit.MILLISECONDS.toDays(diff)}d ago"
            else                                 -> formatDate(timestamp)
        }
    }

    /**
     * Returns true if the given timestamp is before today midnight.
     * Used to highlight overdue tasks in red.
     */
    fun isOverdue(timestamp: Long): Boolean =
        timestamp < System.currentTimeMillis()
}
