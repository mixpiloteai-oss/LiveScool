package com.livemachine.academy.data.assistant

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

/** Historique du chat, sur le téléphone uniquement, séparé par machine. */
class AssistantHistoryRepository(private val dataStore: DataStore<Preferences>) {
    private val json = Json { ignoreUnknownKeys = true }
    private val serializer = ListSerializer(ChatMessage.serializer())

    fun history(machineId: String): Flow<List<ChatMessage>> = dataStore.data.map { decode(it[key(machineId)]) }

    suspend fun append(machineId: String, message: ChatMessage) {
        dataStore.edit { prefs ->
            prefs[key(machineId)] = json.encodeToString(serializer, (decode(prefs[key(machineId)]) + message).takeLast(MAX_STORED))
        }
    }

    suspend fun clear(machineId: String) {
        dataStore.edit { it.remove(key(machineId)) }
    }

    private fun decode(raw: String?): List<ChatMessage> =
        raw?.let { runCatching { json.decodeFromString(serializer, it) }.getOrNull() }.orEmpty()

    private fun key(machineId: String) = stringPreferencesKey("history_$machineId")

    companion object {
        const val MAX_STORED = 40
    }
}
