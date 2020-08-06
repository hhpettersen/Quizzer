package com.example.quizzer.data.datarepo.models

data class QuestionResponse (
    val response_code: Int,
    val results: List<QuestionBool>
)

data class QuestionBool (
    val category: String,
    val type: String,
    val difficulty: String,
    val question: String,
    val correct_answer: Boolean
)