package com.example.quizzer.data.remote

import com.example.quizzer.data.entities.QuestionList
import retrofit2.Response
import retrofit2.http.GET

interface QuestionService {
    @GET("api.php?amount=5&category=9&difficulty=easy&type=boolean")
    suspend fun getQuestions() : Response<QuestionList>
}