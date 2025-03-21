package com.example.questcityproject.data

data class User(
    val id: Int = 0,
    val login: String,
    val email: String,
    val password: String
)

data class Quest(
    val id: Int = 0,
    val title: String,
    val landmarksNumber: Int,
    val description: String?
)

data class Landmark(
    val id: Int = 0,
    val questId: Int,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val title: String?
)