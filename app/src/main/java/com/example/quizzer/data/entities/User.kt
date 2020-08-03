package com.example.quizzer.data.entities

data class User(
    val uid: String? = "",
    val email: String? = "",
    val username: String? = "",
    val game: Score? = Score(0, 0, "")
)