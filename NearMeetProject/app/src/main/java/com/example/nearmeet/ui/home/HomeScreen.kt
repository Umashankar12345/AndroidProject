package com.example.nearmeet.ui.home

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.nearmeet.data.model.Event
import com.example.nearmeet.ui.alerts.AlertsViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.*
import com.google.maps.android.heatmaps.HeatmapTileProvider

private const val MAP_STYLE_JSON = """
[
  {
    "elementType": "geometry",
    "stylers": [
      {
        "color": "#f5f5f5"
      }
    ]
  },
  {
    "featureType": "poi",
    "elementType": "labels.text",
    "stylers": [
      {
        "visibility": "off"
      }
    ]
  },
  {
    "featureType": "road",
    "elementType": "geometry",
    "stylers": [
      {
        "color": "#ffffff"
      }
    ]
  },
  {
    "featureType": "water",
    "elementType": "geometry",
    "stylers": [
      {
        "color": "#e9e9e9"
      }
    ]
  },
  {
    "featureType": "water",
    "elementType": "labels.text.fill",
    "stylers": [
      {
        "color": "#9e9e9e"
      }
    ]
  }
]
"""

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onEventDetail: (String) -> Unit = {},
    onCreateEvent: () -> Unit = {},
    onProfile: () -> Unit = {},
    onAlerts: () -> Unit = {},
    viewModel: HomeViewModel = hiltViewModel(),
    alertsViewModel: AlertsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val alertsState by alertsViewModel.uiState.collectAsStateWithLifecycle()
    
    var showMap by remember { mutableStateOf(true) }
    var showHeatmap by remember { mutableStateOf(false) }
    val categories = listOf("All", "Music", "Sports", "Food", "Art", "Tech", "Social")

    val displayEvents = if (uiState.filteredEvents.isNotEmpty() ||
        uiState.searchQuery.isNotEmpty() ||
        uiState.selectedCategory != "All"
    ) uiState.filteredEvents else uiState.nearbyEvents

    val heatmapData = remember(displayEvents) {
        displayEvents.map { LatLng(it.lat, it.lng) }
    }
    val heatmapTileProvider = remember(heatmapData) {
        if (heatmapData.isNotEmpty()) {
            HeatmapTileProvider.Builder()
                .data(heatmapData)
                .build()
        } else null
    }

    val userLocation = uiState.userLocation ?: LatLng(20.5937, 78.9629)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(userLocation, 12f)
    }

    LaunchedEffect(uiState.userLocation) {
        uiState.userLocation?.let {
            cameraPositionState.position = CameraPosition.fromLatLngZoom(it, 12f)
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = showMap,
                    onClick = { showMap = true },
                    icon = { Icon(Icons.Default.Map, null) },
                    label = { Text("Map") }
                )
                NavigationBarItem(
                    selected = !showMap,
                    onClick = { showMap = false },
                    icon = { Icon(Icons.AutoMirrored.Filled.List, null) },
                    label = { Text("List") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onCreateEvent,
                    icon = {
                        Icon(Icons.Default.Add, null,
                            tint = MaterialTheme.colorScheme.primary)
                    },
                    label = { Text("Create") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onAlerts,
                    icon = {
                        BadgedBox(
                            badge = {
                                if (alertsState.unreadCount > 0) {
                                    Badge {
                                        Text(alertsState.unreadCount.toString())
                                    }
                                }
                            }
                        ) {
                            Icon(Icons.Default.Notifications, null)
                        }
                    },
                    label = { Text("Alerts") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onProfile,
                    icon = { Icon(Icons.Default.Person, null) },
                    label = { Text("Profile") }
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // 1. Refined Search Bar
            SearchBar(
                inputField = {
                    SearchBarDefaults.InputField(
                        query = uiState.searchQuery,
                        onQueryChange = viewModel::onSearchQueryChange,
                        onSearch = {},
                        expanded = false,
                        onExpandedChange = {},
                        placeholder = { 
                            Text(
                                "Search events near you...",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                            ) 
                        },
                        leadingIcon = { Icon(Icons.Default.Search, null, tint = MaterialTheme.colorScheme.primary) }
                    )
                },
                expanded = false,
                onExpandedChange = {},
                shape = CircleShape,
                colors = SearchBarDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {}

            // 2. Refined Filters with high corner radius and spacing
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                items(categories) { category ->
                    val isSelected = uiState.selectedCategory == category
                    FilterChip(
                        selected = isSelected,
                        onClick = { viewModel.onCategorySelect(category) },
                        label = { Text(category, modifier = Modifier.padding(horizontal = 4.dp)) },
                        shape = RoundedCornerShape(24.dp),
                        border = if (isSelected) null else FilterChipDefaults.filterChipBorder(
                            enabled = true,
                            selected = false,
                            borderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                        ),
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            containerColor = Color.Transparent
                        )
                    )
                }
            }

            // Error Message Box
            if (uiState.error != null) {
                ErrorAlertBox(message = uiState.error!!)
            }

            if (showMap) {
                Box(modifier = Modifier.fillMaxSize()) {
                    GoogleMap(
                        modifier = Modifier.fillMaxSize(),
                        cameraPositionState = cameraPositionState,
                        uiSettings = MapUiSettings(
                            zoomControlsEnabled = false,
                            myLocationButtonEnabled = true
                        ),
                        properties = MapProperties(
                            isMyLocationEnabled = true,
                            mapStyleOptions = MapStyleOptions(MAP_STYLE_JSON)
                        )
                    ) {
                        if (showHeatmap && heatmapTileProvider != null) {
                            TileOverlay(tileProvider = heatmapTileProvider)
                        } else {
                            displayEvents.forEach { event ->
                                Marker(
                                    state = MarkerState(position = LatLng(event.lat, event.lng)),
                                    title = event.title,
                                    snippet = "${event.category} • ${event.attendees.size} going",
                                    onClick = {
                                        onEventDetail(event.id)
                                        true
                                    }
                                )
                            }
                        }
                    }

                    // 3. Refined FAB (Heatmap Toggle)
                    LargeFloatingActionButton(
                        onClick = { showHeatmap = !showHeatmap },
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(24.dp)
                            .padding(bottom = 32.dp), // Higher to avoid Google logo
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Icon(
                            if (showHeatmap) Icons.Default.Map else Icons.Default.Whatshot,
                            contentDescription = "Toggle heatmap",
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    if (uiState.isLoading) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                CircularProgressIndicator()
                                Spacer(Modifier.height(16.dp))
                                Text(
                                    "Fetching events near you...",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // ... (List items implementation remains largely same, maybe refined)
                    if (uiState.recommendedEvents.isNotEmpty() && uiState.searchQuery.isEmpty() && uiState.selectedCategory == "All") {
                        item {
                            Text(
                                "Recommended for you",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                items(uiState.recommendedEvents) { event ->
                                    RecommendationCard(event = event, onClick = { onEventDetail(event.id) })
                                }
                            }
                            Spacer(Modifier.height(16.dp))
                            HorizontalDivider()
                            Spacer(Modifier.height(8.dp))
                        }
                    }

                    if (displayEvents.isEmpty()) {
                        item {
                            Box(
                                modifier = Modifier.fillMaxWidth().height(200.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Icon(
                                        Icons.Default.EventBusy,
                                        null,
                                        modifier = Modifier.size(48.dp),
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Spacer(Modifier.height(8.dp))
                                    Text("No events found", color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                            }
                        }
                    } else {
                        items(displayEvents, key = { it.id }) { event ->
                            EventCard(event = event, onClick = { onEventDetail(event.id) })
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ErrorAlertBox(message: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFEBEE) // Light Pink
        ),
        border = BorderStroke(1.dp, Color(0xFFEF5350)), // Muted Red
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                Icons.Default.Error,
                contentDescription = null,
                tint = Color(0xFFC62828) // Dark Crimson
            )
            Text(
                text = message,
                color = Color(0xFFC62828), // Dark Crimson
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun RecommendationCard(event: Event, onClick: () -> Unit) {
    Card(
        onClick = onClick, 
        modifier = Modifier.width(200.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.AutoAwesome, null, modifier = Modifier.size(32.dp), tint = MaterialTheme.colorScheme.primary)
            }
            Spacer(Modifier.height(8.dp))
            Text(event.title, style = MaterialTheme.typography.titleSmall, maxLines = 1)
            Text(event.category, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
        }
    }
}

@Composable
fun EventCard(event: Event, onClick: () -> Unit) {
    Card(
        onClick = onClick, 
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(56.dp),
                shape = RoundedCornerShape(12.dp),
                color = when (event.category) {
                    "Music" -> Color(0xFFE1BEE7) // Light Purple
                    "Sports" -> Color(0xFFBBDEFB) // Light Blue
                    "Food" -> Color(0xFFFFE0B2) // Light Orange
                    else -> MaterialTheme.colorScheme.surfaceVariant
                }
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = when(event.category) {
                            "Music" -> Icons.Default.MusicNote
                            "Sports" -> Icons.Default.SportsBasketball
                            "Food" -> Icons.Default.Restaurant
                            else -> Icons.Default.Event
                        },
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(event.title, style = MaterialTheme.typography.titleMedium, maxLines = 1)
                Text(
                    "${event.category} • ${event.attendees.size} going", 
                    style = MaterialTheme.typography.bodySmall, 
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, null, tint = MaterialTheme.colorScheme.outline)
        }
    }
}
