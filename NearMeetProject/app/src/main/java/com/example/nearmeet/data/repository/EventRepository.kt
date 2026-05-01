package com.example.nearmeet.data.repository

import com.example.nearmeet.data.local.EventDao
import com.example.nearmeet.data.local.EventEntity
import com.example.nearmeet.data.model.Event
import com.example.nearmeet.data.remote.FirestoreDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EventRepository @Inject constructor(
    private val remote: FirestoreDataSource,
    private val dao: EventDao
) {
    suspend fun fetchNearbyEvents(
        lat: Double,
        lng: Double,
        radiusKm: Double
    ) {
        val events = remote.getNearbyEvents(lat, lng, radiusKm)
        val entities = events.map {
            EventEntity(
                id = it.id,
                title = it.title,
                category = it.category,
                lat = it.lat,
                lng = it.lng,
                dateTime = it.dateTime,
                attendeeCount = it.attendees.size
            )
        }
        dao.upsertEvents(entities)
    }

    fun getLocalEvents(): Flow<List<EventEntity>> =
        dao.getAllEvents()

    suspend fun createEvent(event: Event) {
        remote.createEvent(event)
    }

    suspend fun getEventById(eventId: String): Event? {
        return remote.getEventById(eventId)
    }

    suspend fun rsvpToEvent(eventId: String, userId: String) {
        remote.rsvpToEvent(eventId, userId)
    }
}
