package com.college.notetask

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

/**
 * NoteTaskApplication
 *
 * The entry point of the application.
 * @HiltAndroidApp triggers Hilt's code generation and creates the
 * application-level dependency container.
 *
 * We also implement WorkManager's Configuration.Provider so that
 * HiltWorkerFactory can inject dependencies into our Workers.
 */
@HiltAndroidApp
class NoteTaskApplication : Application(), Configuration.Provider {

    // Injected by Hilt — allows Workers to receive @Inject constructors
    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
}
