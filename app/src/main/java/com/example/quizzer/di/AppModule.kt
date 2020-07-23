package com.example.quizzer.di

import android.content.Context
import com.example.quizzer.data.local.AppDatabase
import com.example.quizzer.data.local.AppDatabaseQuiz
import com.example.quizzer.data.local.CharacterDao
import com.example.quizzer.data.local.QuestionDao
import com.example.quizzer.data.remote.CharacterRemoteDataSource
import com.example.quizzer.data.remote.CharacterService
import com.example.quizzer.data.remote.QuestionRemoteDataSource
import com.example.quizzer.data.remote.QuestionService
import com.example.quizzer.data.repository.CharacterRepository
import com.example.quizzer.data.repository.QuestionRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson) : Retrofit = Retrofit.Builder()
        .baseUrl("https://opentdb.com/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

//    @Provides
//    fun provideCharacterService(retrofit: Retrofit): QuestionService = retrofit.create(QuestionService::class.java)

    @Provides
    fun provideQuestionService(retrofit: Retrofit) : QuestionService = retrofit.create(QuestionService::class.java)

//    @Singleton
//    @Provides
//    fun provideCharacterRemoteDataSource(characterService: CharacterService) = CharacterRemoteDataSource(characterService)

    @Singleton
    @Provides
    fun provideQuestionRemoteDataSource(questionService: QuestionService) = QuestionRemoteDataSource(questionService)

//    @Singleton
//    @Provides
//    fun provideDatabase(@ApplicationContext appContext: Context) = AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideDatabaseQuiz(@ApplicationContext appContext: Context) = AppDatabaseQuiz.getDatabase(appContext)

//    @Singleton
//    @Provides
//    fun provideCharacterDao(db: AppDatabase) = db.characterDao()

    @Singleton
    @Provides
    fun provideQuestionDao(db: AppDatabaseQuiz) = db.questionDao()

//    @Singleton
//    @Provides
//    fun provideRepository(remoteDataSource: CharacterRemoteDataSource,
//                          localDataSource: CharacterDao) =
//        CharacterRepository(remoteDataSource, localDataSource)

    @Singleton
    @Provides
    fun provideQuizRepository(remoteDataSource: QuestionRemoteDataSource,
                              localDataSource: QuestionDao) =
        QuestionRepository(remoteDataSource, localDataSource)
}