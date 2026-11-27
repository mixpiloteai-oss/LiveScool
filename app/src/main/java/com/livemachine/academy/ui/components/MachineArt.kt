package com.livemachine.academy.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Album
import androidx.compose.material.icons.outlined.AutoAwesomeMosaic
import androidx.compose.material.icons.outlined.GraphicEq
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.Piano
import androidx.compose.material.icons.outlined.ViewWeek
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.livemachine.academy.data.model.MachineCategory
import com.livemachine.academy.data.model.MachineProfile
import com.livemachine.academy.ui.theme.Acid
import com.livemachine.academy.ui.theme.Magenta
import com.livemachine.academy.ui.theme.MagentaDeep
import com.livemachine.academy.ui.theme.Violet
import com.livemachine.academy.ui.theme.Warning

val MachineCategory.icon: ImageVector
    get() = when (this) {
        MachineCategory.GROOVEBOX -> Icons.Outlined.GridView
        MachineCategory.SAMPLER -> Icons.Outlined.GraphicEq
        MachineCategory.DRUM_MACHINE -> Icons.Outlined.Album
        MachineCategory.SYNTH -> Icons.Outlined.Piano
        MachineCategory.SEQUENCER -> Icons.Outlined.ViewWeek
        MachineCategory.HYBRID -> Icons.Outlined.AutoAwesomeMosaic
    }

private fun MachineCategory.accent(): Color = when (this) {
    MachineCategory.GROOVEBOX -> Magenta
    MachineCategory.SAMPLER -> Color(0xFFFF6FB5)
    MachineCategory.DRUM_MACHINE -> Color(0xFFFF8A5C)
    MachineCategory.SYNTH -> Violet
    MachineCategory.SEQUENCER -> Acid
    MachineCategory.HYBRID -> Color(0xFF6FD3FF)
}

/**
 * Illustration générée de la machine (aucune image constructeur) : dégradé de la catégorie,
 * icône de la catégorie et nom du modèle. La vraie image est la photo que l'utilisateur prend de SA machine.
 */
@Composable
fun MachineIllustration(machine: MachineProfile, modifier: Modifier = Modifier, size: Dp = 72.dp) {
    val accent = machine.category.accent()
    val shape = RoundedCornerShape(size / 5)
    Box(
        modifier
            .size(size)
            .clip(shape)
            .background(Brush.linearGradient(listOf(accent.copy(alpha = 0.55f), MagentaDeep.copy(alpha = 0.55f), Color(0xFF16101A))))
            .border(1.dp, accent.copy(alpha = 0.6f), shape),
    ) {
        Icon(
            machine.category.icon,
            contentDescription = null,
            tint = accent,
            modifier = Modifier.align(Alignment.TopEnd).padding(size / 10).size(size / 3),
        )
        Column(Modifier.align(Alignment.BottomStart).padding(horizontal = size / 10, vertical = size / 12)) {
            Text(
                machine.name,
                style = if (size >= 96.dp) MaterialTheme.typography.titleMedium else MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

/** Pastille « Cours disponible » / « Cours en attente du manuel officiel ». */
@Composable
fun CourseStatusPill(machine: MachineProfile) {
    val color = if (machine.hasCourse) Acid else Warning
    Text(
        machine.courseStatusLabel,
        style = MaterialTheme.typography.labelSmall,
        color = color,
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(color.copy(alpha = 0.14f))
            .padding(horizontal = 8.dp, vertical = 3.dp),
    )
}
