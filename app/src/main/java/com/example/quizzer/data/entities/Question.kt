package com.example.quizzer.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "questions")
data class Question(
    val category: String,
    val type: String,
    val difficulty: String,
    @PrimaryKey
    val question: String,
    val correct_answer: Boolean
)