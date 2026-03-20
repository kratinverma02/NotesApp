package com.college.notetask.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.college.notetask.data.local.dao.NoteDao
import com.college.notetask.data.local.dao.TaskDao
import com.college.notetask.data.local.entity.NoteEntity
import com.college.notetask.data.local.entity.Priority
import com.college.notetask.data.local.entity.TaskEntity
import com.college.notetask.data.local.entity.TaskStatus

/**
 * TypeConverters
 *
 * Room cannot store Enum types directly in SQLite.
 * These converters tell Room how to:
 *   - Save enums → store as String text in DB
 *   - Load enums → read String from DB and convert back to enum
 */
class Converters {

    @TypeConverter
    fun fromPriority(priority: Priority): String = priority.name

    @TypeConverter
    fun toPriority(value: String): Priority = Priority.valueOf(value)

    @TypeConverter
    fun fromTaskStatus(status: TaskStatus): String = status.name

    @TypeConverter
    fun toTaskStatus(value: String): TaskStatus = TaskStatus.valueOf(value)
}

/**
 * AppDatabase
 *
 * The main Room database class.
 * - entities: all tables this database manages
 * - version: increment this when you change schema (and add a Migration)
 * - exportSchema: set to false for now (set true in production apps)
 *
 * This class is a singleton — only ONE instance exists in the app.
 * Hilt creates and provides this instance (see DatabaseModule.kt).
 */
@Database(
    entities = [NoteEntity::class, TaskEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao
    abstract fun taskDao(): TaskDao

    companion object {
        const val DATABASE_NAME = "note_task_db"
    }
}
