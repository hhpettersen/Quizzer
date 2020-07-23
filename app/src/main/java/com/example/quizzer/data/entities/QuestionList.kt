package com.example.quizzer.data.entities

data class QuestionList(
    val response_code: Int,
    val results: List<Question>
)