package com.example.quizzer.ui.start

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quizzer.data.datarepo.models.QuestionResponse
import com.example.quizzer.data.datarepo.network.Coroutines
import com.example.quizzer.data.datarepo.repository.Repository
import com.example.quizzer.data.entities.Game
import com.example.quizzer.data.entities.User
import com.example.quizzer.utils.ValueListenerAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Job

class StartViewModel(
    private val repository: Repository
) : ViewModel() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDatabase: DatabaseReference

    private val _gameInfo = MutableLiveData<Game>()
    val gameInfo: LiveData<Game>
        get() = _gameInfo

    fun fetchData() {
        mAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance().reference

        fun currentReference(): DatabaseReference =
            mDatabase.child("users").child(mAuth.currentUser!!.uid)

        currentReference().addListenerForSingleValueEvent(
            ValueListenerAdapter {
                val user = it.getValue(User::class.java)
                if(user != null) {
                    _gameInfo.value = user.game
                }
            }
        )
    }

    // new api call

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
