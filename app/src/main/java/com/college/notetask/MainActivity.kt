package com.college.notetask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.college.notetask.ui.navigation.NoteTaskNavHost
import com.college.notetask.ui.theme.NoteTaskTheme
import com.college.notetask.viewmodel.ThemeViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * MainActivity
 *
 * Single Activity — all screens are Composable functions.
 * @AndroidEntryPoint enables Hilt injection in this Activity.
 *
 * Steps:
 * 1. Enable edge-to-edge display (modern Android look)
 * 2. Read dark/light theme preference from ThemeViewModel
 * 3. Apply NoteTaskTheme wrapper
 * 4. Launch NavHost (handles all screen navigation)
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // extends UI behind status/nav bars

        setContent {
            // Observe theme preference from DataStore
            val themeViewModel: ThemeViewModel = hiltViewModel()
            val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()

            NoteTaskTheme(darkTheme = isDarkTheme) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Main navigation graph
                    NoteTaskNavHost()
                }
            }
        }
    }
}
