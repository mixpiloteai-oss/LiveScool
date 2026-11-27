package com.livemachine.academy.data.commerce

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import java.util.UUID

/**
 * Identifiant aléatoire de l'installation (UUID v4), sans lien avec l'identité de l'utilisateur.
 * Sert au serveur pour les quotas et pour rattacher un abonnement vérifié à cette installation.
 */
class InstallIdRepository(private val dataStore: DataStore<Preferences>) {
    suspend fun get(): String {
        dataStore.data.first()[KEY]?.let { return it }
        var id = ""
        dataStore.edit { prefs -> id = prefs[KEY] ?: UUID.randomUUID().toString().also { prefs[KEY] = it } }
        return id
    }

    /** Nouvel identifiant : l'usage passé n'est plus rattaché ; un abonnement se récupère par « Restaurer mes achats ». */
    suspend fun reset() {
        dataStore.edit { it.remove(KEY) }
    }

    private companion object {
        val KEY = stringPreferencesKey("install_id")
    }
}
