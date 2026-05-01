package com.example.nearmeet.ui.eventdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nearmeet.data.model.Event
import com.example.nearmeet.data.repository.AuthRepository
import com.example.nearmeet.data.repository.EventRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class EventDetailUiState(
    val event: Event? = null,
    val isLoading: Boolean = false,
    val isRsvped: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class EventDetailViewModel @Inject constructor(
    private val repository: EventRepository,
    private val auth: FirebaseAuth,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val eventId: String = checkNotNull(savedStateHandle["eventId"])

    private val _uiState = MutableStateFlow(EventDetailUiState())
    val uiState: StateFlow<EventDetailUiState> = _uiState.asStateFlow()

    init {
        loadEvent()
    }

    private fun loadEvent() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val event = repository.getEventById(eventId)
            val userId = auth.currentUser?.uid
            val isRsvped = event?.attendees?.contains(userId) ?: false
            _uiState.value = _uiState.value.copy(
                event = event,
                isLoading = false,
                isRsvped = isRsvped
            )
        }
    }

    fun rsvp() {
        val userId = auth.currentUser?.uid ?: return
        viewModelScope.launch {
            repository.rsvpToEvent(eventId, userId)
            _uiState.value = _uiState.value.copy(isRsvped = true)
            loadEvent() // Refresh data
        }
    }
}
