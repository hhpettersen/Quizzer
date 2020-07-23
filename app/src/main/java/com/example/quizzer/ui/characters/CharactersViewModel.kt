package com.example.quizzer.ui.characters

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.quizzer.data.repository.CharacterRepository
import com.example.quizzer.data.repository.QuestionRepository

class CharactersViewModel @ViewModelInject constructor(
    private val repository: QuestionRepository
) : ViewModel() {

    val characters = repository.getQuestions()
}
