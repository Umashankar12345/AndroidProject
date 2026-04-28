package com.example.nearmeet.ui.home

import com.example.nearmeet.data.model.Event

data class HomeUiState(
    val nearbyEvents: List<Event> = emptyList(),
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val selectedCategory: String = "All",
    val radiusKm: Double = 5.0,
    val error: String? = null
)