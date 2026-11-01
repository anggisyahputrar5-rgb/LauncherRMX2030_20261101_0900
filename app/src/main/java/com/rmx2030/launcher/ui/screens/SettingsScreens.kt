package com.rmx2030.launcher.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class MenuItem(
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val color: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    onNavigateToDisplay: () -> Unit,
    onNavigateToTheme: () -> Unit
) {
    val menuItems = listOf(
        MenuItem("Beranda", "Pengaturan utama", Icons.Default.Home, Color(0xFF3B82F6)),
        MenuItem("Tampilan & Tata Letak", "Kustomisasi layar utama", Icons.Default.ViewModule, Color(0xFF8B5CF6)),
        MenuItem("Dock & Bilah Navigasi", "Atur dock dan navigasi", Icons.Default.Dock, Color(0xFF6366F1)),
        MenuItem("Tema & Personalisasi", "Ubah tema dan warna", Icons.Default.Palette, Color(0xFFEC4899)),
        MenuItem("Aplikasi", "Kelola aplikasi & pintasan", Icons.Default.Apps, Color(0xFFF59E0B)),
        MenuItem("Gestur & Interaksi", "Pengaturan gestur", Icons.Default.TouchApp, Color(0xFF10B981)),
        MenuItem("Developer Lab", "Fitur pengembang & debug", Icons.Default.Code, Color(0xFFA855F7)),
        MenuItem("Tentang", "Informasi aplikasi", Icons.Default.Info, Color(0xFF6B7280))
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Launcher RMX2030", fontWeight = FontWeight.Bold)
                        Text("v1.1", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Kembali")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(menuItems) { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            when (item.title) {
                                "Tampilan & Tata Letak" -> onNavigateToDisplay()
                                "Tema & Personalisasi" -> onNavigateToTheme()
                            }
                        },
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(item.icon, contentDescription = null, tint = item.color, modifier = Modifier.size(32.dp))
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(item.title, fontWeight = FontWeight.SemiBold)
                            Text(item.subtitle, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                        }
                        Icon(Icons.Default.ChevronRight, contentDescription = null)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplaySettingsScreen(
    onBack: () -> Unit,
    columns: Int,
    onColumnsChange: (Int) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tampilan & Tata Letak") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Kembali")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Preset Layout", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf(3, 4, 5, 6).forEach { cols ->
                    FilterChip(
                        selected = columns == cols,
                        onClick = { onColumnsChange(cols) },
                        label = { Text("${cols}x5") }
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            Text("Pengaturan Grid", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(16.dp))
            
            Text("Jumlah Kolom: $columns")
            Slider(
                value = columns.toFloat(),
                onValueChange = { onColumnsChange(it.toInt()) },
                valueRange = 3f..6f,
                steps = 2
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeSettingsScreen(
    onBack: () -> Unit,
    currentTheme: String,
    onThemeChange: (String) -> Unit
) {
    val themes = listOf("OBSIDIAN" to "Klasik Gelap", "MIDNIGHT" to "Biru Elegan", "AURORA" to "Modern Terang")
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tema & Personalisasi") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Kembali")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Pilih tema utama launcher.", fontSize = 14.sp)
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                themes.forEach { (name, label) ->
                    Card(
                        modifier = Modifier
                            .width(100.dp)
                            .clickable { onThemeChange(name) },
                        colors = CardDefaults.cardColors(
                            containerColor = if (currentTheme == name) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Column(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(name, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            Text(label, fontSize = 10.sp)
                        }
                    }
                }
            }
        }
    }
}
