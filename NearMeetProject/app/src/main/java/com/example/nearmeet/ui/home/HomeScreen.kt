package com.example.nearmeet.ui.home

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
import androidx.compose.ui.draw.shadow
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
    "elementType": "labels.icon",
    "stylers": [
      {
        "visibility": "off"
      }
    ]
  },
  {
    "elementType": "labels.text.fill",
    "stylers": [
      {
        "color": "#616161"
      }
    ]
  },
  {
    "elementType": "labels.text.stroke",
    "stylers": [
      {
        "color": "#f5f5f5"
      }
    ]
  },
  {
    "featureType": "administrative.land_parcel",
    "elementType": "labels.text.fill",
    "stylers": [
      {
        "color": "#bdbdbd"
      }
    ]
  },
  {
    "featureType": "poi",
    "elementType": "geometry",
    "stylers": [
      {
        "color": "#eeeeee"
      }
    ]
  },
  {
    "featureType": "poi",
    "elementType": "labels.text.fill",
    "stylers": [
      {
        "color": "#757575"
      }
    ]
  },
  {
    "featureType": "poi.park",
    "elementType": "geometry",
    "stylers": [
      {
        "color": "#e5e5e5"
      }
    ]
  },
  {
    "featureType": "poi.park",
    "elementType": "labels.text.fill",
    "stylers": [
      {
        "color": "#9e9e9e"
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
    "featureType": "road.arterial",
    "elementType": "labels.text.fill",
    "stylers": [
      {
        "color": "#757575"
      }
    ]
  },
  {
    "featureType": "road.highway",
    "elementType": "geometry",
    "stylers": [
      {
        "color": "#dadada"
      }
    ]
  },
  {
    "featureType": "road.highway",
    "elementType": "labels.text.fill",
    "stylers": [
      {
        "color": "#616161"
      }
    ]
  },
  {
    "featureType": "road.local",
    "elementType": "labels.text.fill",
    "stylers": [
      {
        "color": "#9e9e9e"
      }
    ]
  },
  {
    "featureType": "transit.line",
    "elementType": "geometry",
    "stylers": [
      {
        "color": "#e5e5e5"
      }
    ]
  },
  {
    "featureType": "transit.station",
    "elementType": "geometry",
    "stylers": [
      {
        "color": "#eeeeee"
      }
    ]
  },
  {
    "featureType": "water",
    "elementType": "geometry",
    "stylers": [
      {
        "color": "#c9c9c9"
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
        containerColor = MaterialTheme.colorScheme.background,
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
            // 1. Refined Search Bar with Shadow
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
                                style = MaterialTheme.typography.bodyLarge
                            ) 
                        },
                        leadingIcon = { Icon(Icons.Default.Search, null, tint = MaterialTheme.colorScheme.primary) }
                    )
                },
                expanded = false,
                onExpandedChange = {},
                shape = CircleShape,
                colors = SearchBarDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .shadow(4.dp, CircleShape) // Added shadow for separation
            ) {}

            // 2. Refined Filters: Soft solid background, no border for unselected
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                items(categories) { category ->
                    val isSelected = uiState.selectedCategory == category
                    FilterChip(
                        selected = isSelected,
                        onClick = { viewModel.onCategorySelect(category) },
                        label = { Text(category) },
                        shape = RoundedCornerShape(24.dp),
                        border = null, // No border as requested
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            containerColor = MaterialTheme.colorScheme.surface, // Solid background
                            labelColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
            }

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
                            myLocationButtonEnabled = false // Keep it clean
                        ),
                        properties = MapProperties(
                            isMyLocationEnabled = true,
                            mapStyleOptions = MapStyleOptions(MAP_STYLE_JSON) // Minimalist Silver Style
                        )
                    ) {
                        if (showHeatmap && heatmapTileProvider != null) {
                            TileOverlay(tileProvider = heatmapTileProvider)
                        } else {
                            displayEvents.forEach { event ->
                                MarkerComposable(
                                    state = MarkerState(position = LatLng(event.lat, event.lng)),
                                    title = event.title,
                                    onClick = {
                                        onEventDetail(event.id)
                                        true
                                    }
                                ) {
                                    // Purple Accent Markers
                                    Surface(
                                        modifier = Modifier.size(36.dp),
                                        shape = CircleShape,
                                        color = MaterialTheme.colorScheme.primary, // Purple accent
                                        border = BorderStroke(2.dp, Color.White),
                                        shadowElevation = 4.dp
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Icon(
                                                imageVector = when(event.category) {
                                                    "Music" -> Icons.Default.MusicNote
                                                    "Sports" -> Icons.Default.SportsBasketball
                                                    "Food" -> Icons.Default.Restaurant
                                                    else -> Icons.Default.LocationOn
                                                },
                                                contentDescription = null,
                                                tint = Color.White,
                                                modifier = Modifier.size(20.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // 3. Spaced FAB matching navigation color tones
                    LargeFloatingActionButton(
                        onClick = { showHeatmap = !showHeatmap },
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(24.dp)
                            .padding(bottom = 32.dp), // Spaced from edge
                        containerColor = MaterialTheme.colorScheme.primaryContainer, // Match nav/accent tone
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
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
                                Text("Fetching events...", style = MaterialTheme.typography.bodyMedium)
                            }
                        }
                    }
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    if (uiState.recommendedEvents.isNotEmpty() && uiState.searchQuery.isEmpty() && uiState.selectedCategory == "All") {
                        item {
                            Text(
                                "Recommended for you",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(bottom = 12.dp)
                            )
                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                items(uiState.recommendedEvents) { event ->
                                    RecommendationCard(event = event, onClick = { onEventDetail(event.id) })
                                }
                            }
                            Spacer(Modifier.height(16.dp))
                            HorizontalDivider()
                            Spacer(Modifier.height(16.dp))
                        }
                    }

                    if (displayEvents.isEmpty()) {
                        item {
                            EmptyState()
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
fun EmptyState() {
    Box(
        modifier = Modifier.fillMaxWidth().height(300.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                Icons.Default.SearchOff,
                null,
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.outline
            )
            Spacer(Modifier.height(16.dp))
            Text(
                "No events found nearby", 
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
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
            containerColor = Color(0xFFFFEBEE)
        ),
        border = BorderStroke(1.dp, Color(0xFFEF5350)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(Icons.Default.Error, null, tint = Color(0xFFC62828))
            Text(message, color = Color(0xFFC62828), style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun RecommendationCard(event: Event, onClick: () -> Unit) {
    Card(
        onClick = onClick, 
        modifier = Modifier.width(220.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
                    .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.AutoAwesome, null, modifier = Modifier.size(40.dp), tint = MaterialTheme.colorScheme.primary)
            }
            Column(modifier = Modifier.padding(12.dp)) {
                Text(event.title, style = MaterialTheme.typography.titleSmall, maxLines = 1)
                Text(event.category, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}

@Composable
fun EventCard(event: Event, onClick: () -> Unit) {
    Card(
        onClick = onClick, 
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(52.dp),
                shape = RoundedCornerShape(14.dp),
                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
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
                        tint = MaterialTheme.colorScheme.primary
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
