package com.example.quizzer.data.entities

data class User(
    val uid: String? = "",
    val email: String? = "",
    val username: String? = "",
    val game: Game? = Game(0, 0, "")
)