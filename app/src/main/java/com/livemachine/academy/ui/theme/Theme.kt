package com.livemachine.academy.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density

private val DarkMagentaScheme = darkColorScheme(
    primary = Magenta,
    onPrimary = Night,
    primaryContainer = MagentaContainer,
    onPrimaryContainer = MagentaLight,
    secondary = Violet,
    onSecondary = Night,
    secondaryContainer = VioletContainer,
    onSecondaryContainer = Violet,
    tertiary = Acid,
    onTertiary = Night,
    background = Night,
    onBackground = TextPrimary,
    surface = Night,
    onSurface = TextPrimary,
    surfaceVariant = NightSurfaceHigh,
    onSurfaceVariant = TextSecondary,
    surfaceContainerLowest = Night,
    surfaceContainerLow = NightSurface,
    surfaceContainer = NightSurface,
    surfaceContainerHigh = NightSurfaceHigh,
    surfaceContainerHighest = NightSurfaceHighest,
    outline = Outline,
    outlineVariant = NightSurfaceHighest,
    error = ErrorRed,
)

/** Thème unique, sombre, accent rose magenta. [textScale] vient des paramètres. */
@Composable
fun LiveMachineTheme(textScale: Float = 1f, content: @Composable () -> Unit) {
    val density = LocalDensity.current
    CompositionLocalProvider(
        LocalDensity provides Density(density.density, density.fontScale * textScale),
    ) {
        MaterialTheme(
            colorScheme = DarkMagentaScheme,
            typography = AppTypography,
            content = content,
        )
    }
}
