package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFD4AF37), // Gold
    onPrimary = Color(0xFF1E1E1E),
    primaryContainer = Color(0xFF3B3210),
    onPrimaryContainer = Color(0xFFFDE990),
    secondary = Color(0xFF4CAF50), // Green
    onSecondary = Color.White,
    secondaryContainer = Color(0xFF133615),
    onSecondaryContainer = Color(0xFFA5F4A8),
    tertiary = Color(0xFFE57373), // Red
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFF4f1818),
    onTertiaryContainer = Color(0xFFf7b5b5),
    background = Color(0xFF121212),
    onBackground = Color(0xFFE0E0E0),
    surface = Color(0xFF1E1E1E),
    onSurface = Color(0xFFE0E0E0),
    surfaceVariant = Color(0xFF2C2C2C),
    onSurfaceVariant = Color(0xFFB0B0B0)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFFD4AF37), // Gold
    onPrimary = Color(0xFF1E1E1E),
    primaryContainer = Color(0xFFFDE990),
    onPrimaryContainer = Color(0xFF3B3210),
    secondary = Color(0xFF4CAF50), // Green for profit
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFA5F4A8),
    onSecondaryContainer = Color(0xFF133615),
    tertiary = Color(0xFFE57373), // Red for loss
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFf7b5b5),
    onTertiaryContainer = Color(0xFF4f1818),
    background = Color(0xFFF5F5F5),
    onBackground = Color(0xFF121212),
    surface = Color.White,
    onSurface = Color(0xFF1E1E1E),
    surfaceVariant = Color(0xFFE0E0E0),
    onSurfaceVariant = Color(0xFF424242)
)

@Composable
fun CapitalGuruTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else DarkColorScheme // Force dark theme for Trading App vibe!

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
