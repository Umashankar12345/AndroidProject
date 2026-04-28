package com.example.nearmeet.domain.usecase
import com.example.nearmeet.data.local.EventEntity
import com.example.nearmeet.data.repository.EventRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNearbyEventsUseCase @Inject constructor(
    private val repository: EventRepository
) {
    suspend operator fun invoke(
        lat: Double,
        lng: Double,
        radiusKm: Double = 5.0
    ) {
        repository.fetchNearbyEvents(lat, lng, radiusKm)
    }

    fun getEvents(): Flow<List<EventEntity>> =
        repository.getLocalEvents()
}