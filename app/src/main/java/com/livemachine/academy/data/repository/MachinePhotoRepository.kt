package com.livemachine.academy.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.exifinterface.media.ExifInterface
import com.livemachine.academy.data.content.MachineCatalog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.io.File

/** Position d'un repère, en coordonnées normalisées (0..1) sur la photo. */
data class PhotoMark(val x: Float, val y: Float)

data class MachinePhotoState(
    val hasPhoto: Boolean = false,
    /** Change à chaque nouvelle photo, pour recharger l'image. */
    val version: Long = 0L,
    val marks: Map<String, PhotoMark> = emptyMap(),
)

/**
 * Photo de chaque machine de l'utilisateur et repères qu'il y a placés.
 * Les photos sont copiées dans le stockage privé de l'app ; rien ne quitte le téléphone.
 * L'Electribe garde les clés et le fichier d'avant le multi-machine (données conservées).
 */
class MachinePhotoRepository(
    private val context: Context,
    private val dataStore: DataStore<Preferences>,
) {
    fun photoFile(machineId: String): File = File(context.filesDir, photoFileName(machineId))

    fun state(machineId: String): Flow<MachinePhotoState> = dataStore.data.map { prefs ->
        val version = prefs[versionKey(machineId)]
        MachinePhotoState(
            hasPhoto = version != null && photoFile(machineId).exists(),
            version = version ?: 0L,
            marks = decodeMarks(prefs[marksKey(machineId)]),
        )
    }

    /** Copie la photo choisie (redimensionnée, orientée selon l'EXIF) et efface les anciens repères. */
    suspend fun importPhoto(machineId: String, uri: Uri): Boolean = withContext(Dispatchers.IO) {
        val resolver = context.contentResolver
        val bounds = BitmapFactory.Options().apply { inJustDecodeBounds = true }
        resolver.openInputStream(uri)?.use { BitmapFactory.decodeStream(it, null, bounds) } ?: return@withContext false
        var sample = 1
        while (maxOf(bounds.outWidth, bounds.outHeight) / sample > MAX_SIZE) sample *= 2
        val decoded = resolver.openInputStream(uri)?.use {
            BitmapFactory.decodeStream(it, null, BitmapFactory.Options().apply { inSampleSize = sample })
        } ?: return@withContext false
        val rotation = resolver.openInputStream(uri)?.use {
            when (ExifInterface(it).getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)) {
                ExifInterface.ORIENTATION_ROTATE_90 -> 90f
                ExifInterface.ORIENTATION_ROTATE_180 -> 180f
                ExifInterface.ORIENTATION_ROTATE_270 -> 270f
                else -> 0f
            }
        } ?: 0f
        val bitmap = if (rotation == 0f) decoded else
            Bitmap.createBitmap(decoded, 0, 0, decoded.width, decoded.height, Matrix().apply { postRotate(rotation) }, true)
        photoFile(machineId).outputStream().use { bitmap.compress(Bitmap.CompressFormat.JPEG, 90, it) }
        dataStore.edit {
            it[versionKey(machineId)] = System.currentTimeMillis()
            it.remove(marksKey(machineId))
        }
        true
    }

    suspend fun setMark(machineId: String, controlId: String, mark: PhotoMark?) {
        dataStore.edit { prefs ->
            val marks = decodeMarks(prefs[marksKey(machineId)]).toMutableMap()
            if (mark == null) marks.remove(controlId) else marks[controlId] = mark
            prefs[marksKey(machineId)] = encodeMarks(marks)
        }
    }

    suspend fun deletePhoto(machineId: String) {
        withContext(Dispatchers.IO) { photoFile(machineId).delete() }
        dataStore.edit {
            it.remove(versionKey(machineId))
            it.remove(marksKey(machineId))
        }
    }

    companion object {
        private const val MAX_SIZE = 1600

        /** L'Electribe garde les noms d'origine ; les autres machines sont suffixées. */
        private fun isLegacy(machineId: String) = machineId == MachineCatalog.ELECTRIBE_ID

        fun photoFileName(machineId: String) = if (isLegacy(machineId)) "machine_photo.jpg" else "machine_photo_$machineId.jpg"
        fun versionKeyName(machineId: String) = if (isLegacy(machineId)) "photo_version" else "photo_version_$machineId"
        fun marksKeyName(machineId: String) = if (isLegacy(machineId)) "photo_marks" else "photo_marks_$machineId"
        private fun versionKey(machineId: String) = longPreferencesKey(versionKeyName(machineId))
        private fun marksKey(machineId: String) = stringPreferencesKey(marksKeyName(machineId))

        fun encodeMarks(marks: Map<String, PhotoMark>): String =
            marks.entries.joinToString(";") { (id, m) -> "$id:${m.x}:${m.y}" }

        fun decodeMarks(raw: String?): Map<String, PhotoMark> =
            raw.orEmpty().split(";").mapNotNull { entry ->
                val parts = entry.split(":")
                if (parts.size != 3) return@mapNotNull null
                val x = parts[1].toFloatOrNull() ?: return@mapNotNull null
                val y = parts[2].toFloatOrNull() ?: return@mapNotNull null
                parts[0] to PhotoMark(x, y)
            }.toMap()
    }
}
