package com.example.nearmeet.data.model

data class User(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val profileImageUrl: String? = null,
    val fcmToken: String = "",
    val joinedEvents: List<String> = emptyList()
)
