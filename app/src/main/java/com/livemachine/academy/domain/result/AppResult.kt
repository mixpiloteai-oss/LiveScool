package com.livemachine.academy.domain.result

/** Erreurs typées des futurs services (compte, synchronisation, IA, analyse audio). */
sealed interface AppError {
    /** Pas de réseau ou serveur injoignable : l'app continue en local. */
    data object Offline : AppError
    data object Unauthorized : AppError
    data object RequiresPremium : AppError
    /** Fonction pas encore disponible dans cette version. */
    data object NotAvailable : AppError
    data class Unexpected(val message: String) : AppError
}

/** Résultat d'une opération pouvant échouer, sans exception qui remonte jusqu'à l'interface. */
sealed interface AppResult<out T> {
    data class Success<T>(val value: T) : AppResult<T>
    data class Failure(val error: AppError) : AppResult<Nothing>
}

/** Convertit une exception inattendue en [AppError.Unexpected]. */
suspend fun <T> appResultOf(block: suspend () -> T): AppResult<T> =
    try {
        AppResult.Success(block())
    } catch (e: kotlinx.coroutines.CancellationException) {
        throw e
    } catch (e: Exception) {
        AppResult.Failure(AppError.Unexpected(e.message ?: e::class.java.simpleName))
    }
