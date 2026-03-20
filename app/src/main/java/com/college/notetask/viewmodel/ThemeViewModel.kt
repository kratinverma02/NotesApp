package com.college.notetask.viewmodel

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ThemeViewModel
 *
 * Persists the user's dark/light theme choice in DataStore
 * (survives app restarts — unlike SharedPreferences, it's
 * coroutine-safe and type-safe).
 *
 * MainActivity observes isDarkTheme and passes it to NoteTaskTheme.
 */
@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : ViewModel() {

    private val DARK_THEME_KEY = booleanPreferencesKey("dark_theme")

    /** Emits the current theme preference (false = light, true = dark). */
    val isDarkTheme: StateFlow<Boolean> = dataStore.data
        .map { prefs -> prefs[DARK_THEME_KEY] ?: false }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false
        )

    /** Flips the stored theme preference. */
    fun toggleTheme() {
        viewModelScope.launch {
            dataStore.edit { prefs ->
                prefs[DARK_THEME_KEY] = !(prefs[DARK_THEME_KEY] ?: false)
            }
        }
    }
}
