package com.livemachine.academy.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.livemachine.academy.data.content.MachineCatalog
import com.livemachine.academy.data.model.AppSettings
import com.livemachine.academy.data.model.TextSize
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsRepository(private val dataStore: DataStore<Preferences>) {

    val settings: Flow<AppSettings> = dataStore.data.map { prefs ->
        AppSettings(
            textSize = prefs[TEXT_SIZE]?.let { name -> TextSize.entries.firstOrNull { it.name == name } }
                ?: TextSize.NORMAL,
            keepScreenOn = prefs[KEEP_SCREEN_ON] ?: true,
            unlockAllModules = prefs[UNLOCK_ALL] ?: false,
            selectedMachineId = prefs[SELECTED_MACHINE] ?: MachineCatalog.ELECTRIBE_ID,
        )
    }

    suspend fun setTextSize(size: TextSize) {
        dataStore.edit { it[TEXT_SIZE] = size.name }
    }

    suspend fun setKeepScreenOn(enabled: Boolean) {
        dataStore.edit { it[KEEP_SCREEN_ON] = enabled }
    }

    suspend fun setUnlockAllModules(enabled: Boolean) {
        dataStore.edit { it[UNLOCK_ALL] = enabled }
    }

    suspend fun setSelectedMachine(machineId: String) {
        dataStore.edit { it[SELECTED_MACHINE] = machineId }
    }

    private companion object {
        val TEXT_SIZE = stringPreferencesKey("text_size")
        val KEEP_SCREEN_ON = booleanPreferencesKey("keep_screen_on")
        val UNLOCK_ALL = booleanPreferencesKey("unlock_all_modules")
        val SELECTED_MACHINE = stringPreferencesKey("selected_machine")
    }
}
