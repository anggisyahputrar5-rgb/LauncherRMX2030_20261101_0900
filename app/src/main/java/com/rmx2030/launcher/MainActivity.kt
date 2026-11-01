package com.rmx2030.launcher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rmx2030.launcher.data.PreferencesManager
import com.rmx2030.launcher.ui.screens.*
import com.rmx2030.launcher.ui.theme.LauncherTheme
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val prefsManager = PreferencesManager(this)
        
        setContent {
            val columns by prefsManager.gridColumns.collectAsState(initial = 4)
            val themeName by prefsManager.themeName.collectAsState(initial = "MIDNIGHT")
            
            LauncherTheme(themeName = themeName) {
                val navController = rememberNavController()
                
                NavHost(navController = navController, startDestination = "home") {
                    composable("home") {
                        HomeScreen(
                            columns = columns,
                            onSettingsClick = { navController.navigate("settings") }
                        )
                    }
                    composable("settings") {
                        SettingsScreen(
                            onBack = { navController.popBackStack() },
                            onNavigateToDisplay = { navController.navigate("display_settings") },
                            onNavigateToTheme = { navController.navigate("theme_settings") }
                        )
                    }
                    composable("display_settings") {
                        DisplaySettingsScreen(
                            onBack = { navController.popBackStack() },
                            columns = columns,
                            onColumnsChange = { newCols ->
                                runBlocking { prefsManager.saveGridColumns(newCols) }
                            }
                        )
                    }
                    composable("theme_settings") {
                        ThemeSettingsScreen(
                            onBack = { navController.popBackStack() },
                            currentTheme = themeName,
                            onThemeChange = { newTheme ->
                                runBlocking { prefsManager.saveThemeName(newTheme) }
                            }
                        )
                    }
                }
            }
        }
    }
}
