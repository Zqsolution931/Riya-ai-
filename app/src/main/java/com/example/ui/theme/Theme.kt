package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val RiyaColorScheme = darkColorScheme(
    primary = RiyaCyan,
    onPrimary = RiyaBgDark,
    primaryContainer = RiyaSurfaceElevated,
    onPrimaryContainer = RiyaCyan,
    secondary = RiyaGold,
    onSecondary = RiyaBgDark,
    secondaryContainer = RiyaSurfaceElevated,
    onSecondaryContainer = RiyaGold,
    tertiary = RiyaNeonGreen,
    onTertiary = RiyaBgDark,
    background = RiyaCanvas,
    onBackground = RiyaTextPrimary,
    surface = RiyaSurface,
    onSurface = RiyaTextPrimary,
    surfaceVariant = RiyaSurfaceElevated,
    onSurfaceVariant = RiyaTextSecondary,
    outline = RiyaBorderCyan,
    error = RiyaNeonRed
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = RiyaColorScheme,
        typography = Typography,
        content = content
    )
}
