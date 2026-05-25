package com.example.nearmeet.ui.home

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.nearmeet.data.model.Event
import com.example.nearmeet.ui.alerts.AlertsViewModel
import com.example.nearmeet.ui.theme.PrimaryPurple
import com.example.nearmeet.ui.theme.SecondaryPink
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.*
import com.google.maps.android.heatmaps.HeatmapTileProvider

private const val MAP_STYLE_JSON = """
[
  { "elementType": "geometry", "stylers": [{"color": "#212121"}] },
  { "elementType": "labels.icon", "stylers": [{"visibility": "off"}] },
  { "elementType": "labels.text.fill", "stylers": [{"color": "#757575"}] },
  { "featureType": "road", "elementType": "geometry", "stylers": [{"color": "#2c2c2c"}] },
  { "featureType": "water", "elementType": "geometry", "stylers": [{"color": "#000000"}] }
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
    val categories = listOf("All", "Music", "Sports", "Food", "Art", "Tech")

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { viewModel.loadLocationAndEvents() }

    LaunchedEffect(Unit) {
        val hasPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        if (!hasPermission) {
            locationPermissionLauncher.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION))
        }
    }

    val displayEvents = if (uiState.filteredEvents.isNotEmpty() || uiState.searchQuery.isNotEmpty() || uiState.selectedCategory != "All") 
        uiState.filteredEvents else uiState.nearbyEvents

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(uiState.userLocation ?: LatLng(20.59, 78.96), 12f)
    }

    Scaffold(
        bottomBar = { PremiumBottomNav(currentTab = if(showMap) 0 else 1, onTabClick = { showMap = it == 0 }, onProfile, onAlerts, onCreateEvent) }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            // Map Layer
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                uiSettings = MapUiSettings(zoomControlsEnabled = false, myLocationButtonEnabled = true),
                properties = MapProperties(isMyLocationEnabled = true, mapStyleOptions = MapStyleOptions(MAP_STYLE_JSON))
            ) {
                displayEvents.forEach { event ->
                    MarkerComposable(
                        state = MarkerState(position = LatLng(event.lat, event.lng)),
                        title = event.title
                    ) {
                        EventMarker(category = event.category)
                    }
                }
            }

            // Top Overlay: Search & Categories
            Column(modifier = Modifier.fillMaxWidth().padding(top = 16.dp)) {
                GlassSearchBar(
                    query = uiState.searchQuery,
                    onQueryChange = viewModel::onSearchQueryChange
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(categories) { category ->
                        PremiumCategoryChip(
                            category = category,
                            isSelected = uiState.selectedCategory == category,
                            onClick = { viewModel.onCategorySelect(category) }
                        )
                    }
                }
            }

            // Bottom Overlay: Floating Event Cards
            AnimatedVisibility(
                visible = showMap && displayEvents.isNotEmpty(),
                enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
                modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 20.dp)
            ) {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    state = rememberLazyListState()
                ) {
                    items(displayEvents) { event ->
                        FloatingEventCard(event = event, onClick = { onEventDetail(event.id) })
                    }
                }
            }
        }
    }
}

@Composable
fun EventMarker(category: String) {
    val color = when(category) {
        "Music" -> PrimaryPurple
        "Sports" -> Color(0xFFFF5252)
        "Food" -> Color(0xFFFFAB40)
        else -> SecondaryPink
    }
    
    Box(contentAlignment = Alignment.Center) {
        Surface(
            modifier = Modifier.size(36.dp),
            shape = CircleShape,
            color = color,
            border = BorderStroke(2.dp, Color.White),
            shadowElevation = 8.dp
        ) {
            Icon(
                imageVector = Icons.Default.Place,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.padding(6.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GlassSearchBar(query: String, onQueryChange: (String) -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(56.dp)
            .shadow(12.dp, CircleShape),
        color = Color.White.copy(alpha = 0.9f),
        shape = CircleShape,
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.3f))
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Search, null, tint = PrimaryPurple)
            TextField(
                value = query,
                onValueChange = onQueryChange,
                placeholder = { Text("Search vibes nearby...") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        }
    }
}

@Composable
fun PremiumCategoryChip(category: String, isSelected: Boolean, onClick: () -> Unit) {
    val background = if (isSelected) 
        Brush.horizontalGradient(listOf(PrimaryPurple, SecondaryPink))
    else 
        Brush.linearGradient(listOf(Color.White, Color.White))

    Surface(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .clickable { onClick() },
        color = Color.Transparent,
        border = if (!isSelected) BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.5f)) else null
    ) {
        Box(
            modifier = Modifier
                .background(background)
                .padding(horizontal = 20.dp, vertical = 10.dp)
        ) {
            Text(
                text = category,
                color = if (isSelected) Color.White else Color.Gray,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun FloatingEventCard(event: Event, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .width(280.dp)
            .height(140.dp)
            .shadow(16.dp, RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = "https://images.unsplash.com/photo-1501281668745-f7f57925c3b4?auto=format&fit=crop&w=300&q=80", // Placeholder
                contentDescription = null,
                modifier = Modifier.width(100.dp).fillMaxHeight(),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(12.dp).fillMaxSize()) {
                Text(event.title, fontWeight = FontWeight.Bold, maxLines = 1, fontSize = 16.sp)
                Text(event.category, color = PrimaryPurple, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.weight(1f))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Star, null, tint = Color(0xFFFFD600), modifier = Modifier.size(16.dp))
                    Text(" 4.9 ", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(Icons.Default.AccessTime, null, modifier = Modifier.size(16.dp))
                    Text(" 7 PM", fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
fun PremiumBottomNav(
    currentTab: Int,
    onTabClick: (Int) -> Unit,
    onProfile: () -> Unit,
    onAlerts: () -> Unit,
    onCreate: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(72.dp)
            .shadow(24.dp, RoundedCornerShape(36.dp)),
        shape = RoundedCornerShape(36.dp),
        color = Color.White
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavIcon(Icons.Default.Map, "Map", currentTab == 0) { onTabClick(0) }
            NavIcon(Icons.Default.List, "List", currentTab == 1) { onTabClick(1) }
            
            FloatingActionButton(
                onClick = onCreate,
                containerColor = PrimaryPurple,
                contentColor = Color.White,
                shape = CircleShape,
                modifier = Modifier.size(56.dp)
            ) {
                Icon(Icons.Default.Add, null)
            }
            
            NavIcon(Icons.Default.Notifications, "Alerts", false) { onAlerts() }
            NavIcon(Icons.Default.Person, "Profile", false) { onProfile() }
        }
    }
}

@Composable
fun NavIcon(icon: ImageVector, label: String, isSelected: Boolean, onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (isSelected) PrimaryPurple else Color.Gray,
            modifier = Modifier.size(28.dp)
        )
    }
}
