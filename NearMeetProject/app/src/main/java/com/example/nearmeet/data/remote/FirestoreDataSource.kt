package com.example.nearmeet.data.remote

import com.example.nearmeet.data.model.Event
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    suspend fun getNearbyEvents(
        lat: Double,
        lng: Double,
        radiusKm: Double
    ): List<Event> {
        val delta = radiusKm / 111.0
        return firestore.collection("events")
            .whereGreaterThan("lat", lat - delta)
            .whereLessThan("lat", lat + delta)
            .get().await()
            .toObjects(Event::class.java)
    }

    suspend fun createEvent(event: Event) {
        firestore.collection("events")
            .document(event.id)
            .set(event).await()
    }

    suspend fun getEventById(eventId: String): Event? {
        return firestore.collection("events")
            .document(eventId)
            .get().await()
            .toObject(Event::class.java)
    }

    suspend fun rsvpToEvent(eventId: String, userId: String) {
        firestore.collection("events")
            .document(eventId)
            .update("attendees", FieldValue.arrayUnion(userId))
            .await()
    }
}
