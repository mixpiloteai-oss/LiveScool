package com.livemachine.academy.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.preferencesDataStoreFile
import com.livemachine.academy.BuildConfig
import com.livemachine.academy.data.account.AccountRepository
import com.livemachine.academy.data.assistant.AssistantHistoryRepository
import com.livemachine.academy.data.assistant.HttpAssistantClient
import com.livemachine.academy.data.audio.AudioDecoder
import com.livemachine.academy.data.audio.AudioRecorder
import com.livemachine.academy.data.audio.HttpAudioAnalysisClient
import com.livemachine.academy.data.audio.TempAudioStore
import com.livemachine.academy.data.account.GuestAccountRepository
import com.livemachine.academy.data.commerce.EntitlementRepository
import com.livemachine.academy.data.commerce.BillingManager
import com.livemachine.academy.data.commerce.FreeEntitlementRepository
import com.livemachine.academy.data.commerce.InstallIdRepository
import com.livemachine.academy.data.commerce.RemoteEntitlementRepository
import com.livemachine.academy.data.content.MachineCatalog
import com.livemachine.academy.data.services.AudioAnalyzer
import com.livemachine.academy.data.services.MusicAssistant
import com.livemachine.academy.data.services.UnavailableAudioAnalyzer
import com.livemachine.academy.data.services.UnavailableMusicAssistant
import com.livemachine.academy.data.sync.LocalOnlyProgressSync
import com.livemachine.academy.data.sync.ProgressSync
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
private val Context.assistantStore: DataStore<Preferences> by preferencesDataStore(name = "assistant_history")
private val Context.entitlementStore: DataStore<Preferences> by preferencesDataStore(name = "entitlement")
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

    /** Adresse publique du backend (aucun secret) ; vide = assistant, analyse et offres non configurés. */
    val assistantConfigured: Boolean = BuildConfig.ASSISTANT_URL.isNotBlank()
    val installIdRepository = InstallIdRepository(appContext.settingsStore)
    private val installId: suspend () -> String? = { installIdRepository.get() }

    /** Droits : uniquement d'après un jeton signé par le serveur et vérifié avec la clé publique de l'app. */
    val entitlementRepository: EntitlementRepository =
        if (assistantConfigured && BuildConfig.ENTITLEMENT_PUBLIC_KEY.isNotBlank()) {
            RemoteEntitlementRepository(appContext.entitlementStore, installIdRepository, BuildConfig.ASSISTANT_URL, BuildConfig.ENTITLEMENT_PUBLIC_KEY)
        } else {
            FreeEntitlementRepository()
        }

    /** Offre appliquée (modules et défis Premium) seulement quand les paiements sont activés dans ce build. */
    val commerceEnforced: Boolean = BuildConfig.BILLING_ENABLED && entitlementRepository is RemoteEntitlementRepository
    val billingManager = BillingManager(appContext, entitlementRepository, installIdRepository, enabled = commerceEnforced)
    val accountRepository: AccountRepository = GuestAccountRepository()
    val progressSync: ProgressSync = LocalOnlyProgressSync()
    val audioAnalyzer: AudioAnalyzer =
        if (assistantConfigured) HttpAudioAnalysisClient(BuildConfig.ASSISTANT_URL, installId = installId) else UnavailableAudioAnalyzer()
    val audioDecoder = AudioDecoder(appContext)
    val audioRecorder = AudioRecorder(appContext)
    val tempAudioStore = TempAudioStore(appContext.cacheDir)

    init {
        // Aucun audio ne survit à un redémarrage de l'app (fichiers temporaires d'une session interrompue).
        tempAudioStore.clear()
        // Droits rafraîchis au démarrage (sans réseau : le dernier jeton signé reste valable jusqu'à son expiration).
        scope.launch { entitlementRepository.refresh() }
    }
    val musicAssistant: MusicAssistant =
        if (assistantConfigured) HttpAssistantClient(BuildConfig.ASSISTANT_URL, installId = installId) else UnavailableMusicAssistant()
    val assistantHistoryRepository = AssistantHistoryRepository(appContext.assistantStore)
    val settingsRepository = SettingsRepository(appContext.settingsStore)
    val photoRepository = MachinePhotoRepository(appContext, appContext.photoStore)
}
