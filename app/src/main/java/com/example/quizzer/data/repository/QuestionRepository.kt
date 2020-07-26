package com.example.quizzer.data.repository

import com.example.quizzer.data.local.QuestionDao
import com.example.quizzer.data.remote.QuestionRemoteDataSource
import com.example.quizzer.utils.performGetOperation
import javax.inject.Inject

class QuestionRepository @Inject constructor(
    private val remoteDataSource: QuestionRemoteDataSource,
    private val localDataSource: QuestionDao
) {

    fun getQuestions() = performGetOperation(
        databaseQuery = { localDataSource.getQuestions() },
        networkCall = { remoteDataSource.getQuestions() },
        saveCallResult = { localDataSource.insertAll(it.results)}
    )

    fun deleteQuestions() = localDataSource.deleteQuestions()
}