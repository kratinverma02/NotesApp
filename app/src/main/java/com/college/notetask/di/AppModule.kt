package com.college.notetask.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.college.notetask.data.local.dao.NoteDao
import com.college.notetask.data.local.dao.TaskDao
import com.college.notetask.data.local.database.AppDatabase
import com.college.notetask.data.repository.NoteRepository
import com.college.notetask.data.repository.NoteRepositoryImpl
import com.college.notetask.data.repository.TaskRepository
import com.college.notetask.data.repository.TaskRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// DataStore extension — creates a single DataStore instance per app
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

/**
 * DatabaseModule
 *
 * Tells Hilt HOW to create the database-related dependencies.
 * @InstallIn(SingletonComponent::class) → these live as long as the app
 * @Provides → Hilt calls these functions to build the dependency
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        AppDatabase.DATABASE_NAME
    )
        .fallbackToDestructiveMigration() // Dev only: clears DB on schema change
        .build()

    @Provides
    @Singleton
    fun provideNoteDao(db: AppDatabase): NoteDao = db.noteDao()

    @Provides
    @Singleton
    fun provideTaskDao(db: AppDatabase): TaskDao = db.taskDao()

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        context.dataStore
}

/**
 * RepositoryModule
 *
 * Binds interfaces to their implementations.
 * When something asks for NoteRepository, Hilt provides NoteRepositoryImpl.
 *
 * @Binds is more efficient than @Provides for interface binding.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindNoteRepository(impl: NoteRepositoryImpl): NoteRepository

    @Binds
    @Singleton
    abstract fun bindTaskRepository(impl: TaskRepositoryImpl): TaskRepository
}
