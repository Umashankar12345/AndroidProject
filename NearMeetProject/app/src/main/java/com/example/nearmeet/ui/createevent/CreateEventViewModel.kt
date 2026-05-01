package com.example.nearmeet.ui.createevent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nearmeet.data.model.Event
import com.example.nearmeet.data.repository.AuthRepository
import com.example.nearmeet.data.repository.EventRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CreateEventViewModel @Inject constructor(
    private val repository: EventRepository,
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _isSaved = MutableStateFlow(false)
    val isSaved: StateFlow<Boolean> = _isSaved

    fun saveEvent(
        title: String,
        description: String,
        category: String,
        lat: Double,
        lng: Double,
        dateTime: Long
    ) {
        viewModelScope.launch {
            val event = Event(
                id = UUID.randomUUID().toString(),
                title = title,
                description = description,
                category = category,
                lat = lat,
                lng = lng,
                dateTime = dateTime,
                creatorId = auth.currentUser?.uid ?: ""
            )
            repository.createEvent(event)
            _isSaved.value = true
        }
    }
}
