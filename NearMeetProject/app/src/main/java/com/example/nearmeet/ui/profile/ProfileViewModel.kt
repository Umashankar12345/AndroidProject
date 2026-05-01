package com.example.nearmeet.ui.profile

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

data class ProfileUiState(
    val userName: String = "",
    val email: String = "",
    val createdEvents: List<Event> = emptyList(),
    val joinedEvents: List<Event> = emptyList(),
    val isLoading: Boolean = false
)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val repository: EventRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        loadProfileData()
    }

    private fun loadProfileData() {
        val user = auth.currentUser ?: return
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                userName = user.displayName ?: "User",
                email = user.email ?: "Anonymous",
                isLoading = true
            )
            
            try {
                val created = repository.getEventsByCreator(user.uid)
                val joined = repository.getJoinedEvents(user.uid)
                _uiState.value = _uiState.value.copy(
                    createdEvents = created,
                    joinedEvents = joined,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }

    fun logout() {
        auth.signOut()
    }
}
