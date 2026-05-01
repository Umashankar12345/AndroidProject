package com.example.nearmeet.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nearmeet.data.model.Event
import com.example.nearmeet.domain.usecase.GetNearbyEventsUseCase
import com.example.nearmeet.domain.util.LocationManager
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getNearbyEvents: GetNearbyEventsUseCase,
    private val locationManager: LocationManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadLocationAndEvents()
        observeLocalEvents()
    }

    private fun observeLocalEvents() {
        viewModelScope.launch {
            getNearbyEvents.getEvents().collect { entities ->
                val events = entities.map { entity ->
                    Event(
                        id = entity.id,
                        title = entity.title,
                        category = entity.category,
                        lat = entity.lat,
                        lng = entity.lng,
                        dateTime = entity.dateTime,
                        attendees = List(entity.attendeeCount) { "" } // Stub for attendee list
                    )
                }
                _uiState.update { state ->
                    val filtered = filterEvents(events, state.searchQuery, state.selectedCategory)
                    state.copy(nearbyEvents = events, filteredEvents = filtered)
                }
            }
        }
    }

    fun loadLocationAndEvents() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val location = locationManager.getCurrentLocation()
                ?: LatLng(20.5937, 78.9629) // India fallback
            _uiState.update { it.copy(userLocation = location) }
            
            try {
                getNearbyEvents(location.latitude, location.longitude, _uiState.value.radiusKm)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _uiState.update { state ->
            val filtered = filterEvents(state.nearbyEvents, query, state.selectedCategory)
            state.copy(searchQuery = query, filteredEvents = filtered)
        }
    }

    fun onCategorySelect(category: String) {
        _uiState.update { state ->
            val filtered = filterEvents(state.nearbyEvents, state.searchQuery, category)
            state.copy(selectedCategory = category, filteredEvents = filtered)
        }
    }

    private fun filterEvents(
        events: List<Event>,
        query: String,
        category: String
    ): List<Event> {
        return events.filter { event ->
            val matchesQuery = query.isEmpty() ||
                event.title.contains(query, ignoreCase = true)
            val matchesCategory = category == "All" ||
                event.category == category
            matchesQuery && matchesCategory
        }
    }
}
