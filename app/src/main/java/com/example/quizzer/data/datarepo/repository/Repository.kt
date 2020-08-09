package com.example.quizzer.data.datarepo.repository

import com.example.quizzer.data.datarepo.network.Api
import com.example.quizzer.data.datarepo.network.SafeApiRequest

class Repository (private val api: Api) : SafeApiRequest() {
    suspend fun  getQuestionsBool() = apiRequest { api.getQuestionsBool() }
}

