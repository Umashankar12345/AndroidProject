package com.example.nearmeet.data.model

data class Notification(
    val id: String = "",
    val userId: String = "",
    val eventId: String = "",
    val eventName: String = "",
    val title: String = "",
    val message: String = "",
    val type: NotificationType = NotificationType.REMINDER,
    val timestamp: Long = 0L,
    val isRead: Boolean = false
)

enum class NotificationType {
    REMINDER,
    NEARBY,
    RSVP
}
