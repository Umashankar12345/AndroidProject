package com.example.nearmeet.ui.profile

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onBack: () -> Unit,
    onLogout: () -> Unit,
    onEventClick: (String) -> Unit,
    onShowcaseClick: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        viewModel.logout()
                        onLogout()
                    }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = "Logout",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )
        }
    ) { padding ->
        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(contentAlignment = Alignment.BottomEnd) {
                            Surface(
                                modifier = Modifier.size(100.dp),
                                shape = CircleShape,
                                color = MaterialTheme.colorScheme.primaryContainer
                            ) {
                                if (!uiState.user?.profileImageUrl.isNullOrEmpty()) {
                                    AsyncImage(
                                        model = uiState.user?.profileImageUrl,
                                        contentDescription = null,
                                        modifier = Modifier.fillMaxSize().clip(CircleShape),
                                        contentScale = ContentScale.Crop
                                    )
                                } else {
                                    Icon(Icons.Default.Person, null, modifier = Modifier.padding(20.dp))
                                }
                            }
                        }
                        Spacer(Modifier.height(8.dp))
                        Text(uiState.user?.name ?: "User", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                        Text(uiState.user?.email ?: "", color = Color.Gray)
                    }
                }

                // Map Fix
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth().clickable {
                            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                data = Uri.fromParts("package", context.packageName, null)
                            }
                            context.startActivity(intent)
                        },
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.LocationOn, null, tint = Color.Blue)
                            Spacer(Modifier.width(12.dp))
                            Text("Fix Permissions", fontWeight = FontWeight.Bold)
                        }
                    }
                }

                // Created Events
                if (uiState.createdEvents.isNotEmpty()) {
                    item { Text("My Created Events", fontWeight = FontWeight.Bold) }
                    items(uiState.createdEvents) { event ->
                        // Using Positional Arguments to avoid "No parameter found" errors
                        EventItemCard(
                            event.title,
                            event.location,
                            event.date
                        ) { onEventClick(event.id) }
                    }
                }

                // Joined Events
                if (uiState.joinedEvents.isNotEmpty()) {
                    item { Text("Events I'm Attending", fontWeight = FontWeight.Bold) }
                    items(uiState.joinedEvents) { event ->
                        // Using Positional Arguments to avoid "No parameter found" errors
                        EventItemCard(
                            event.title,
                            event.location,
                            event.date
                        ) { onEventClick(event.id) }
                    }
                }
            }
        }
    }
}

// Renamed function to avoid conflicts and cleaned up parameters
@Composable
fun EventItemCard(
    title: String,
    location: String,
    date: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(title, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.LocationOn, null, Modifier.size(14.dp), tint = Color.Gray)
                Text(location, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.DateRange, null, Modifier.size(14.dp), tint = Color.Gray)
                Text(date, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }
        }
    }
}