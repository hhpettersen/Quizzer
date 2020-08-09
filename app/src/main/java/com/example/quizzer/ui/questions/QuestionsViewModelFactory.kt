package com.example.quizzer.ui.questions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.quizzer.data.datarepo.repository.Repository

@Suppress("UNCHECKED_CAST")
class QuestionsViewModelFactory (
    private val repository: Repository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return QuestionsViewModel(
            repository
        ) as T
    }
}