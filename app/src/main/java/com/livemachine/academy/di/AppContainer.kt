package com.livemachine.academy.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.livemachine.academy.data.repository.CourseRepository
import com.livemachine.academy.data.repository.MachinePhotoRepository
import com.livemachine.academy.data.repository.ProgressRepository
import com.livemachine.academy.data.repository.SettingsRepository

private val Context.progressStore: DataStore<Preferences> by preferencesDataStore(name = "progress")
private val Context.settingsStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
private val Context.photoStore: DataStore<Preferences> by preferencesDataStore(name = "machine_photo")

/** Injection de dépendances manuelle : suffisante pour une app de cette taille. */
class AppContainer(context: Context) {
    private val appContext = context.applicationContext

    val courseRepository = CourseRepository()
    val progressRepository = ProgressRepository(appContext.progressStore)
    val settingsRepository = SettingsRepository(appContext.settingsStore)
    val photoRepository = MachinePhotoRepository(appContext, appContext.photoStore)
}
