package com.rmx2030.launcher.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val ObsidianColors = darkColorScheme(
    primary = Color(0xFF8B5CF6),
    background = Color(0xFF0A0A0F),
    surface = Color(0xFF14141F)
)

private val MidnightColors = darkColorScheme(
    primary = Color(0xFF3B82F6),
    background = Color(0xFF0B1426),
    surface = Color(0xFF111D35)
)

private val AuroraColors = lightColorScheme(
    primary = Color(0xFF6366F1),
    background = Color(0xFFF8FAFC),
    surface = Color(0xFFFFFFFF)
)

@Composable
fun LauncherTheme(themeName: String = "MIDNIGHT", content: @Composable () -> Unit) {
    val colorScheme = when (themeName) {
        "OBSIDIAN" -> ObsidianColors
        "AURORA" -> AuroraColors
        else -> MidnightColors
    }
    MaterialTheme(colorScheme = colorScheme, content = content)
}
