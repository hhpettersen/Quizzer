package com.example.quizzer.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.quizzer.data.entities.Character
import com.example.quizzer.data.entities.Question

@Database(entities = [Question::class], version = 4, exportSchema = false)
abstract class AppDatabaseQuiz : RoomDatabase() {

    abstract fun questionDao(): QuestionDao

    companion object {
        @Volatile private var instance: AppDatabaseQuiz? = null

        fun getDatabase(context: Context): AppDatabaseQuiz =
            instance ?: synchronized(this) { instance ?: buildDatabase(context).also { instance = it } }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, AppDatabaseQuiz::class.java, "questions")
                .fallbackToDestructiveMigration()
                .build()
    }

}