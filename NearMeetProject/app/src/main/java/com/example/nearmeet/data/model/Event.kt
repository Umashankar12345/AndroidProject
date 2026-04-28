package com.example.nearmeet.data.model

data class Event(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val category: String = "",
    val lat: Double = 0.0,
    val lng: Double = 0.0,
    val dateTime: Long = 0L,
    val creatorId: String = "",
    val attendees: List<String> = emptyList(),
    val distanceKm: Double = 0.0
)