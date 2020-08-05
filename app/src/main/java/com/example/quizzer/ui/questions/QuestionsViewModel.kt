package com.example.quizzer.ui.questions

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizzer.data.repository.QuestionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuestionsViewModel @ViewModelInject constructor(
    private val repository: QuestionRepository
) : ViewModel() {
    val questions = repository.getQuestions()

    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score

    private val _questionIndex = MutableLiveData<Int>()
    val questionIndex: LiveData<Int>
        get() = _questionIndex

    init {
        _score.value = 0
        _questionIndex.value = 0
    }

    fun endQuiz() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteQuestions()
        }
    }

    fun onCorrectAnswer() {
        _score.value = (_score.value)?.plus(1)
        _questionIndex.value = (_questionIndex.value)?.plus(1)
    }

    fun onWrongAnswer() {
        if(_score.value!! > 0) {
            _score.value = (_score.value?.minus(1))
        }
        _questionIndex.value = (_questionIndex.value)?.plus(1)
    }
}