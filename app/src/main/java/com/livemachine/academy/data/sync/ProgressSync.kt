package com.livemachine.academy.data.sync

import com.livemachine.academy.data.model.UserProgress
import com.livemachine.academy.domain.result.AppError
import com.livemachine.academy.domain.result.AppResult

/**
 * Synchronisation future de la progression, machine par machine (même découpage que les
 * fichiers locaux). La source de vérité reste le téléphone ; le serveur ne fait que copier.
 */
interface ProgressSync {
    suspend fun push(machineId: String, progress: UserProgress): AppResult<Unit>
    suspend fun pull(machineId: String): AppResult<UserProgress?>
}

/** Version actuelle : pas de synchronisation, tout reste local. */
class LocalOnlyProgressSync : ProgressSync {
    override suspend fun push(machineId: String, progress: UserProgress): AppResult<Unit> = AppResult.Failure(AppError.NotAvailable)
    override suspend fun pull(machineId: String): AppResult<UserProgress?> = AppResult.Failure(AppError.NotAvailable)
}
