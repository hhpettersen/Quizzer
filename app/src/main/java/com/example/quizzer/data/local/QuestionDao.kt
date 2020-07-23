package com.example.quizzer.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.quizzer.data.entities.Question

@Dao
interface QuestionDao {

    @Query("SELECT * FROM questions")
    fun getQuestions() : LiveData<List<Question>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(questions: List<Question>)
}