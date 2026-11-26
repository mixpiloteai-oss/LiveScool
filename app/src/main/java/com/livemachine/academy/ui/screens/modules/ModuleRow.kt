package com.livemachine.academy.ui.screens.modules

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
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
        }
        Spacer(Modifier.height(12.dp))
        ProgressLine(item.fraction)
        Spacer(Modifier.height(4.dp))
        Text(
            if (item.total == 0) "Leçons à rédiger depuis le manuel" else "${item.completed} / ${item.total} leçons",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}
