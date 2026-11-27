package com.livemachine.academy.data.account

import com.livemachine.academy.domain.result.AppError
import com.livemachine.academy.domain.result.AppResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

/** État du compte. Le compte reste facultatif : l'app fonctionne entièrement en invité. */
sealed interface AccountState {
    data object Guest : AccountState
    data class SignedIn(val userId: String, val displayName: String?) : AccountState
}

interface AccountRepository {
    val state: Flow<AccountState>
    suspend fun signOut(): AppResult<Unit>
}

/** Version actuelle : invité uniquement, aucune donnée envoyée. */
class GuestAccountRepository : AccountRepository {
    override val state: Flow<AccountState> = flowOf(AccountState.Guest)
    override suspend fun signOut(): AppResult<Unit> = AppResult.Failure(AppError.NotAvailable)
}
