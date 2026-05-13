package com.example.nearmeet.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nearmeet.data.model.Event
import com.example.nearmeet.data.model.User
import com.example.nearmeet.data.repository.EventRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProfileUiState(
    val user: User? = null,
    val createdEvents: List<Event> = emptyList(),
    val joinedEvents: List<Event> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
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
        val firebaseUser = auth.currentUser ?: return
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            try {
                val userData = repository.getUserById(firebaseUser.uid) ?: User(
                    uid = firebaseUser.uid,
                    name = firebaseUser.displayName ?: "User",
                    email = firebaseUser.email ?: ""
                )
                
                val created = repository.getEventsByCreator(firebaseUser.uid)
                val joined = repository.getJoinedEvents(firebaseUser.uid)
                
                _uiState.value = _uiState.value.copy(
                    user = userData,
                    createdEvents = created,
                    joinedEvents = joined,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false, 
                    error = e.message
                )
            }
        }
    }

    fun logout() {
        auth.signOut()
    }
}
