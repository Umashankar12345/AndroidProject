package com.example.nearmeet.domain.util

import com.example.nearmeet.data.model.Event
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventRecommendation @Inject constructor() {

    // Learns from user's RSVP history and recommends events
    fun getRecommendedEvents(
        allEvents: List<Event>,
        rsvpdEventIds: List<String>
    ): List<Event> {
        if (rsvpdEventIds.size < 3) return emptyList()

        // Find which categories user attends most
        val rsvpdEvents = allEvents.filter { it.id in rsvpdEventIds }
        val categoryCount = rsvpdEvents
            .groupBy { it.category }
            .mapValues { it.value.size }

        val topCategory = categoryCount.maxByOrNull { it.value }?.key
            ?: return emptyList()

        // Return events in top category not yet RSVP'd
        return allEvents.filter { event ->
            event.category == topCategory &&
            event.id !in rsvpdEventIds
        }.take(5)
    }

    // Simple scoring based on category match + distance
    fun scoreEvent(
        event: Event,
        preferredCategories: List<String>,
        userLat: Double,
        userLng: Double
    ): Double {
        val categoryScore = if (event.category in preferredCategories) 1.0 else 0.0
        val distanceScore = 1.0 / (GeoUtils.distanceKm(
            userLat, userLng, event.lat, event.lng
        ) + 1.0)
        return categoryScore * 0.6 + distanceScore * 0.4
    }
}
