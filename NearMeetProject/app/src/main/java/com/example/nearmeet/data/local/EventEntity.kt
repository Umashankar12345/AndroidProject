package com.example.nearmeet.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
data class EventEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val category: String,
    val lat: Double,
    val lng: Double,
    val dateTime: Long,
    val attendeeCount: Int
)