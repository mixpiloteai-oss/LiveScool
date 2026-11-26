package com.livemachine.academy.ui.screens.machine

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.livemachine.academy.data.content.MachineContent
import com.livemachine.academy.data.model.ContentStatus
import com.livemachine.academy.ui.components.AppTopBar
import com.livemachine.academy.ui.components.AwaitingManualNotice
import com.livemachine.academy.ui.components.Eyebrow
import com.livemachine.academy.ui.components.HeroBanner
import com.livemachine.academy.ui.components.ScreenPadding
import com.livemachine.academy.ui.components.SectionCard
import com.livemachine.academy.ui.components.StatusBadge

/** Fiche de la machine (pas de simulateur : uniquement des repères pour la vraie machine). */
@Composable
fun MachineScreen(onOpenSettings: () -> Unit) {
    Column(Modifier.fillMaxSize()) {
        AppTopBar(title = "La machine", onOpenSettings = onOpenSettings)
        LazyColumn(contentPadding = ScreenPadding, verticalArrangement = Arrangement.spacedBy(12.dp)) {
            item {
                HeroBanner {
                    Eyebrow(MachineContent.brand, color = MaterialTheme.colorScheme.onPrimaryContainer)
                    Text(MachineContent.modelLabel, style = MaterialTheme.typography.headlineSmall)
                    Spacer(Modifier.height(8.dp))
                    Text(MachineContent.modelNote, style = MaterialTheme.typography.bodyMedium)
                }
            }
            item { AwaitingManualNotice() }
            items(MachineContent.plannedSections) { section ->
                SectionCard {
                    Text(section, style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(8.dp))
                    StatusBadge(ContentStatus.AWAITING_MANUAL)
                }
            }
        }
    }
}
