# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.

# ── Keep Room entities and DAOs ────────────────────────────────
-keep class com.college.notetask.data.local.entity.** { *; }
-keep class com.college.notetask.data.local.dao.** { *; }

# ── Keep Hilt generated classes ───────────────────────────────
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }

# ── Keep Kotlin data classes (used in StateFlow) ──────────────
-keep class com.college.notetask.domain.model.** { *; }
-keep class com.college.notetask.viewmodel.**UiState { *; }

# ── Keep WorkManager worker classes ───────────────────────────
-keep class com.college.notetask.utils.ReminderWorker { *; }

# ── Coroutines ────────────────────────────────────────────────
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
