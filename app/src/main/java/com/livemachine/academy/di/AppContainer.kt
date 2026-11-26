package com.livemachine.academy.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.preferencesDataStoreFile
import com.livemachine.academy.data.content.MachineCatalog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
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
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    /**
     * Un fichier de progression par machine : « progress » (historique) pour l'Electribe,
     * « progress_<id> » pour les autres. Créés une seule fois, le conteneur étant unique.
     */
    val progressRepository = ProgressRepository(
        MachineCatalog.all.associate { m ->
            m.id to if (m.id == MachineCatalog.ELECTRIBE_ID) appContext.progressStore
            else PreferenceDataStoreFactory.create(scope = scope) { appContext.preferencesDataStoreFile("progress_${m.id}") }
        },
    )

    init {
        scope.launch { progressRepository.migrateLegacy() }
    }
    val settingsRepository = SettingsRepository(appContext.settingsStore)
    val photoRepository = MachinePhotoRepository(appContext, appContext.photoStore)
}
