package com.livemachine.academy.ui.screens.modules

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.WorkspacePremium
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.livemachine.academy.ui.components.NumberBadge
import com.livemachine.academy.ui.components.ProgressLine
import com.livemachine.academy.ui.components.SectionCard
import com.livemachine.academy.ui.screens.ModuleProgress
import com.livemachine.academy.ui.theme.Acid

@Composable
fun ModuleRow(item: ModuleProgress, highlighted: Boolean = false, onClick: () -> Unit) {
    SectionCard(onClick = onClick) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            NumberBadge(item.module.number, highlighted = highlighted || item.isDone)
            Spacer(Modifier.width(14.dp))
            Column(Modifier.weight(1f)) {
                Text(item.module.title, style = MaterialTheme.typography.titleMedium)
                Text(
                    item.module.summary,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            when {
                !item.unlocked -> Icon(Icons.Outlined.Lock, contentDescription = "Verrouillé", tint = MaterialTheme.colorScheme.outline)
                item.competenceAcquired -> Icon(Icons.Outlined.WorkspacePremium, contentDescription = "Compétence acquise", tint = Acid)
            }
        }
        Spacer(Modifier.height(12.dp))
        ProgressLine(item.fraction)
        Spacer(Modifier.height(4.dp))
        val status = when {
            item.premiumLocked -> "Premium · inclus dans l'abonnement"
            !item.unlocked -> "Verrouillé · valide le module précédent"
            item.competenceAcquired -> "Compétence acquise"
            item.validated -> "Module validé"
            else -> null
        }
        Text(
            "${item.completed} / ${item.total} leçons" +
                (item.finalScore?.let { " · quiz final ${it.correct}/${it.total}" } ?: "") +
                (status?.let { " · $it" } ?: ""),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}
