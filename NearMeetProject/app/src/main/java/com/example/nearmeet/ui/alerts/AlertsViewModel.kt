package com.example.nearmeet.ui.alerts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nearmeet.data.model.Notification
import com.example.nearmeet.data.repository.EventRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AlertsUiState(
    val notifications: List<Notification> = emptyList(),
    val unreadCount: Int = 0,
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class AlertsViewModel @Inject constructor(
    private val repository: EventRepository,
    private val auth: FirebaseAuth
) : ViewModel() {

    private val userId = auth.currentUser?.uid ?: ""

    val uiState: StateFlow<AlertsUiState> = repository.getNotifications(userId)
        .map { notifications ->
            AlertsUiState(
                notifications = notifications,
                unreadCount = notifications.count { !it.isRead },
                isLoading = false
            )
        }
        .onStart { emit(AlertsUiState(isLoading = true)) }
        .catch { e -> emit(AlertsUiState(error = e.message, isLoading = false)) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = AlertsUiState(isLoading = true)
        )

    fun markAsRead(notificationId: String) {
        viewModelScope.launch {
            repository.markNotificationAsRead(notificationId)
        }
    }

    fun markAllAsRead() {
        viewModelScope.launch {
            repository.markAllNotificationsAsRead(userId)
        }
    }
}
