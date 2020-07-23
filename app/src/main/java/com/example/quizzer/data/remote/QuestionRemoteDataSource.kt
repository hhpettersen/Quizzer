package com.example.quizzer.data.remote

import javax.inject.Inject

class QuestionRemoteDataSource @Inject constructor(
    private val questionService: QuestionService
): BaseDataSourceQuiz() {
    suspend fun getQuestions() = getResult { questionService.getQuestions() }
}