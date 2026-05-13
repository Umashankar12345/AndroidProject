package com.example.nearmeet.data.remote

import com.example.nearmeet.data.model.Event
import com.example.nearmeet.data.model.Notification
import com.example.nearmeet.data.model.User
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
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

    suspend fun cancelRsvp(eventId: String, userId: String) {
        firestore.collection("events")
            .document(eventId)
            .update("attendees", FieldValue.arrayRemove(userId))
            .await()
    }

    suspend fun getEventsByCreator(userId: String): List<Event> {
        return firestore.collection("events")
            .whereEqualTo("creatorId", userId)
            .get().await()
            .toObjects(Event::class.java)
    }

    suspend fun getJoinedEvents(userId: String): List<Event> {
        return firestore.collection("events")
            .whereArrayContains("attendees", userId)
            .get().await()
            .toObjects(Event::class.java)
    }

    suspend fun getUserById(userId: String): User? {
        return firestore.collection("users")
            .document(userId)
            .get().await()
            .toObject(User::class.java)
    }

    fun getNotifications(userId: String): Flow<List<Notification>> = callbackFlow {
        val subscription = firestore.collection("notifications")
            .whereEqualTo("userId", userId)
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    trySend(snapshot.toObjects(Notification::class.java))
                }
            }
        awaitClose { subscription.remove() }
    }

    suspend fun markNotificationAsRead(notificationId: String) {
        firestore.collection("notifications")
            .document(notificationId)
            .update("isRead", true)
            .await()
    }

    suspend fun markAllNotificationsAsRead(userId: String) {
        val batch = firestore.batch()
        val unreadNotifications = firestore.collection("notifications")
            .whereEqualTo("userId", userId)
            .whereEqualTo("isRead", false)
            .get().await()

        for (doc in unreadNotifications) {
            batch.update(doc.reference, "isRead", true)
        }
        batch.commit().await()
    }
}
