package com.example.nearmeet.domain.usecase

import com.example.nearmeet.data.model.Event
import com.example.nearmeet.data.repository.EventRepository
import javax.inject.Inject

class CreateEventUseCase @Inject constructor(
    private val repository: EventRepository
) {
    suspend operator fun invoke(event: Event) {
        repository.fetchNearbyEvents(
            event.lat, event.lng, 5.0
        )
    }
}