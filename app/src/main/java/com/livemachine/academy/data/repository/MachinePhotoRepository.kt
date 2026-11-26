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
 * Photo de la machine de l'utilisateur et repères qu'il y a placés.
 * La photo est copiée dans le stockage privé de l'app ; rien ne quitte le téléphone.
 */
class MachinePhotoRepository(
    private val context: Context,
    private val dataStore: DataStore<Preferences>,
) {
    val photoFile: File get() = File(context.filesDir, PHOTO_FILE)

    val state: Flow<MachinePhotoState> = dataStore.data.map { prefs ->
        MachinePhotoState(
            hasPhoto = prefs[VERSION] != null && photoFile.exists(),
            version = prefs[VERSION] ?: 0L,
            marks = decodeMarks(prefs[MARKS]),
        )
    }

    /** Copie la photo choisie (redimensionnée, orientée selon l'EXIF) et efface les anciens repères. */
    suspend fun importPhoto(uri: Uri): Boolean = withContext(Dispatchers.IO) {
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
        photoFile.outputStream().use { bitmap.compress(Bitmap.CompressFormat.JPEG, 90, it) }
        dataStore.edit {
            it[VERSION] = System.currentTimeMillis()
            it.remove(MARKS)
        }
        true
    }

    suspend fun setMark(controlId: String, mark: PhotoMark?) {
        dataStore.edit { prefs ->
            val marks = decodeMarks(prefs[MARKS]).toMutableMap()
            if (mark == null) marks.remove(controlId) else marks[controlId] = mark
            prefs[MARKS] = encodeMarks(marks)
        }
    }

    suspend fun deletePhoto() {
        withContext(Dispatchers.IO) { photoFile.delete() }
        dataStore.edit {
            it.remove(VERSION)
            it.remove(MARKS)
        }
    }

    companion object {
        private const val PHOTO_FILE = "machine_photo.jpg"
        private const val MAX_SIZE = 1600
        private val VERSION = longPreferencesKey("photo_version")
        private val MARKS = stringPreferencesKey("photo_marks")

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
