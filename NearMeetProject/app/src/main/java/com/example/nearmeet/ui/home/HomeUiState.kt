package com.example.nearmeet.ui.home

import com.example.nearmeet.data.model.Event
import com.google.android.gms.maps.model.LatLng

data class HomeUiState(
    val nearbyEvents: List<Event> = emptyList(),
    val filteredEvents: List<Event> = emptyList(),
    val recommendedEvents: List<Event> = emptyList(),
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val selectedCategory: String = "All",
    val radiusKm: Double = 5.0,
    val userLocation: LatLng? = null,
    val error: String? = null
)
