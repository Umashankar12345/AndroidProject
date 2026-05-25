package com.example.nearmeet.ui.home

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.nearmeet.data.model.Event
import com.example.nearmeet.ui.alerts.AlertsViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.*
import com.google.maps.android.heatmaps.HeatmapTileProvider

private const val MAP_STYLE_JSON = """
[
  { "elementType": "geometry", "stylers": [{"color": "#f5f5f5"}] },
  { "elementType": "labels.icon", "stylers": [{"visibility": "off"}] },
  { "featureType": "road", "elementType": "geometry", "stylers": [{"color": "#ffffff"}] },
  { "featureType": "water", "elementType": "geometry", "stylers": [{"color": "#e9e9e9"}] }
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
    val context = LocalContext.current
    
    var showMap by remember { mutableStateOf(true) }
    var showHeatmap by remember { mutableStateOf(false) }
    val categories = listOf("All", "Music", "Sports", "Food", "Art", "Tech", "Social")

    // Permission Check
    val hasLocationPermission = ContextCompat.checkSelfPermission(
        context, Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { viewModel.loadLocationAndEvents() }

    LaunchedEffect(Unit) {
        if (!hasLocationPermission) {
            locationPermissionLauncher.launch(arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ))
        }
    }

    val displayEvents = if (uiState.filteredEvents.isNotEmpty() ||
        uiState.searchQuery.isNotEmpty() ||
        uiState.selectedCategory != "All"
    ) uiState.filteredEvents else uiState.nearbyEvents

    // Heatmap data
    val heatmapTileProvider = remember(displayEvents) {
        if (displayEvents.isNotEmpty()) {
            val data = displayEvents.map { LatLng(it.lat, it.lng) }
            HeatmapTileProvider.Builder().data(data).build()
        } else null
    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(uiState.userLocation ?: LatLng(20.5937, 78.9629), 12f)
    }

    LaunchedEffect(uiState.userLocation) {
        uiState.userLocation?.let {
            cameraPositionState.animate(CameraUpdateFactory.newLatLngZoom(it, 12f))
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            NavigationBar(tonalElevation = 8.dp) {
                NavigationBarItem(selected = showMap, onClick = { showMap = true }, icon = { Icon(Icons.Default.Map, null) }, label = { Text("Map") })
                NavigationBarItem(selected = !showMap, onClick = { showMap = false }, icon = { Icon(Icons.AutoMirrored.Filled.List, null) }, label = { Text("List") })
                NavigationBarItem(selected = false, onClick = onCreateEvent, icon = { Icon(Icons.Default.Add, null, tint = MaterialTheme.colorScheme.primary) }, label = { Text("Create") })
                NavigationBarItem(selected = false, onClick = onAlerts, icon = { BadgedBox(badge = { if (alertsState.unreadCount > 0) Badge { Text(alertsState.unreadCount.toString()) } }) { Icon(Icons.Default.Notifications, null) } }, label = { Text("Alerts") })
                NavigationBarItem(selected = false, onClick = onProfile, icon = { Icon(Icons.Default.Person, null) }, label = { Text("Profile") })
            }
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            SearchBar(
                inputField = {
                    SearchBarDefaults.InputField(
                        query = uiState.searchQuery,
                        onQueryChange = viewModel::onSearchQueryChange,
                        onSearch = {},
                        expanded = false,
                        onExpandedChange = {},
                        placeholder = { Text("Search events near you...") },
                        leadingIcon = { Icon(Icons.Default.Search, null, tint = MaterialTheme.colorScheme.primary) }
                    )
                },
                expanded = false,
                onExpandedChange = {},
                shape = CircleShape,
                colors = SearchBarDefaults.colors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp).shadow(3.dp, CircleShape)
            ) {}

            LazyRow(contentPadding = PaddingValues(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(bottom = 16.dp)) {
                items(categories) { category ->
                    FilterChip(
                        selected = uiState.selectedCategory == category,
                        onClick = { viewModel.onCategorySelect(category) },
                        label = { Text(category) },
                        shape = RoundedCornerShape(24.dp),
                        colors = FilterChipDefaults.filterChipColors(selectedContainerColor = MaterialTheme.colorScheme.primaryContainer, containerColor = Color.White)
                    )
                }
            }

            if (uiState.error != null) ErrorAlertBox(message = uiState.error!!)

            if (showMap) {
                Box(modifier = Modifier.fillMaxSize()) {
                    GoogleMap(
                        modifier = Modifier.fillMaxSize(),
                        cameraPositionState = cameraPositionState,
                        uiSettings = MapUiSettings(zoomControlsEnabled = false, myLocationButtonEnabled = hasLocationPermission),
                        properties = MapProperties(isMyLocationEnabled = hasLocationPermission, mapStyleOptions = MapStyleOptions(MAP_STYLE_JSON))
                    ) {
                        if (showHeatmap && heatmapTileProvider != null) {
                            TileOverlay(tileProvider = heatmapTileProvider)
                        } else {
                            displayEvents.forEach { event ->
                                MarkerComposable(state = MarkerState(position = LatLng(event.lat, event.lng)), title = event.title, onClick = { onEventDetail(event.id); true }) {
                                    Surface(modifier = Modifier.size(32.dp), shape = CircleShape, color = MaterialTheme.colorScheme.primary, border = BorderStroke(2.dp, Color.White), shadowElevation = 4.dp) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Icon(imageVector = when(event.category) { "Music" -> Icons.Default.MusicNote; "Sports" -> Icons.Default.SportsBasketball; "Food" -> Icons.Default.Restaurant; else -> Icons.Default.LocationOn }, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                                        }
                                    }
                                }
                            }
                        }
                    }
                    LargeFloatingActionButton(onClick = { showHeatmap = !showHeatmap }, modifier = Modifier.align(Alignment.BottomEnd).padding(end = 24.dp, bottom = 32.dp), containerColor = MaterialTheme.colorScheme.primaryContainer, shape = RoundedCornerShape(20.dp)) {
                        Icon(if (showHeatmap) Icons.Default.Map else Icons.Default.Whatshot, null)
                    }
                    if (uiState.isLoading) Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
                }
            } else {
                LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    if (displayEvents.isEmpty()) item { EmptyState() }
                    else items(displayEvents, key = { it.id }) { event -> EventCard(event = event, onClick = { onEventDetail(event.id) }) }
                }
            }
        }
    }
}

@Composable
fun ErrorAlertBox(message: String) {
    Card(modifier = Modifier.fillMaxWidth().padding(16.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE)), border = BorderStroke(1.dp, Color(0xFFEF5350)), shape = RoundedCornerShape(12.dp)) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Error, null, tint = Color(0xFFC62828))
            Spacer(Modifier.width(12.dp))
            Text(message, color = Color(0xFFC62828))
        }
    }
}

@Composable
fun EmptyState() {
    Box(modifier = Modifier.fillMaxWidth().height(300.dp), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(Icons.Default.SearchOff, null, modifier = Modifier.size(64.dp), tint = MaterialTheme.colorScheme.outline)
            Spacer(Modifier.height(16.dp))
            Text("No events found nearby", style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Composable
fun EventCard(event: Event, onClick: () -> Unit) {
    Card(onClick = onClick, shape = RoundedCornerShape(20.dp)) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Surface(modifier = Modifier.size(52.dp), shape = RoundedCornerShape(14.dp), color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)) {
                Box(contentAlignment = Alignment.Center) { Icon(Icons.Default.Event, null, tint = MaterialTheme.colorScheme.primary) }
            }
            Spacer(Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(event.title, style = MaterialTheme.typography.titleMedium)
                Text("${event.category} • ${event.attendees.size} going", style = MaterialTheme.typography.bodySmall)
            }
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, null)
        }
    }
}
