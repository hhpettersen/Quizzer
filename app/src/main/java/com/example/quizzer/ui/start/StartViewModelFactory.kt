package com.example.quizzer.ui.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.quizzer.data.datarepo.repository.Repository

@Suppress("UNCHECKED_CAST")
class StartViewModelFactory (
    private val repository: Repository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return StartViewModel(
            repository
        ) as T
    }
}