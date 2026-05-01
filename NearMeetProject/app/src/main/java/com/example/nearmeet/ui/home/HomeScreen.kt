package com.example.nearmeet.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onCreateEvent: () -> Unit,
    onEventDetail: (String) -> Unit,
    onProfile: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showMap by remember { mutableStateOf(true) }

    // Initial load - India center
    LaunchedEffect(Unit) {
        viewModel.loadNearbyEvents(20.5937, 78.9629)
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onCreateEvent) {
                Icon(Icons.Default.Add, contentDescription = "Add Event")
            }
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = showMap,
                    onClick = { showMap = true },
                    icon = { Icon(Icons.Default.Place, null) },
                    label = { Text("Map") }
                )
                NavigationBarItem(
                    selected = !showMap,
                    onClick = { showMap = false },
                    icon = { Icon(Icons.Default.List, null) },
                    label = { Text("List") }
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (showMap) {
                NearMeetMap(uiState.nearbyEvents, onEventDetail)
            } else {
                EventsList(uiState, onEventDetail)
            }
            
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun NearMeetMap(
    events: List<com.example.nearmeet.data.model.Event>,
    onMarkerClick: (String) -> Unit
) {
    val initialPos = LatLng(20.5937, 78.9629)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(initialPos, 5f)
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        events.forEach { event ->
            Marker(
                state = MarkerState(position = LatLng(event.lat, event.lng)),
                title = event.title,
                snippet = "Tap for details",
                onInfoWindowClick = { onMarkerClick(event.id) }
            )
        }
    }
}

@Composable
fun EventsList(uiState: HomeUiState, onEventDetail: (String) -> Unit) {
    if (uiState.nearbyEvents.isEmpty()) {
        Box(Modifier.fillMaxSize()) {
            Text(
                text = "No events nearby",
                modifier = Modifier.align(Alignment.Center),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(uiState.nearbyEvents) { event ->
                EventListCard(event = event, onClick = { onEventDetail(event.id) })
            }
        }
    }
}

@Composable
fun EventListCard(event: com.example.nearmeet.data.model.Event, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = event.title,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = event.category,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${event.attendees.size} going",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
