package com.livemachine.academy.data.audio

import com.livemachine.academy.data.assistant.ContextItem
import com.livemachine.academy.data.assistant.MachineInfo
import kotlinx.serialization.Serializable

/** Contrat v1 `POST /v1/audio-analysis` (voir backend/assistant). */
@Serializable
data class AudioAnalysisRequest(
    val machine: MachineInfo,
    val style: String,
    val goal: String,
    val level: String,
    val context: List<ContextItem>,
    val audio: AudioPayload,
)

@Serializable data class AudioPayload(val format: String, val base64: String)

/** Ligne de résultat déjà rédigée par le serveur ; [kind] = « mesure » ou « estimation ». */
@Serializable data class MeasureItem(val label: String, val value: String, val kind: String = "mesure")

@Serializable
data class AudioAnalysisResult(
    val summary: List<MeasureItem> = emptyList(),
    val advice: String? = null,
    val adviceError: String? = null,
)
