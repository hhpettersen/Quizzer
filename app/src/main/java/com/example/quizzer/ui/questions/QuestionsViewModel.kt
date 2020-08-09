package com.example.quizzer.ui.questions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quizzer.data.datarepo.models.QuestionResponse
import com.example.quizzer.data.datarepo.network.Coroutines
import com.example.quizzer.data.datarepo.repository.Repository
import kotlinx.coroutines.Job

class QuestionsViewModel (
    private val repository: Repository
) : ViewModel() {
//    val questions = repository.getQuestions()

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

//    fun endQuiz() {
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.deleteQuestions()
//        }
//    }

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

    // New API call

    private lateinit var job: Job

    private val _q = MutableLiveData<QuestionResponse>()
    val q : LiveData<QuestionResponse>
        get() = _q

    fun getQuestionsBool() {
        job = Coroutines.ioThenMain(
            { repository.getQuestionsBool() },
            { _q.value = it }
        )
    }

    override fun onCleared() {
        super.onCleared()
        if(::job.isInitialized) job.cancel()
    }
}