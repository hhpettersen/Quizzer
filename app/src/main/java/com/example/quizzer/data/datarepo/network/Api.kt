package com.example.quizzer.data.datarepo.network

import com.example.quizzer.data.datarepo.models.QuestionResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

const val BASE_URL = "https://opentdb.com/"

interface Api {
    @GET("api.php?amount=5&category=9&difficulty=easy&type=boolean")
    suspend fun getQuestionsBool() : Response<QuestionResponse>

    companion object {
        operator fun invoke() : Api {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Api::class.java)
        }
    }
}