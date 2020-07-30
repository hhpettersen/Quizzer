package com.example.quizzer.ui.Start

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quizzer.data.entities.Score
import com.example.quizzer.utils.ValueListenerAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class StartViewModel : ViewModel() {
    private var mScore: Int? = null
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDatabase: DatabaseReference

    private val _highscore = MutableLiveData<Score>()
    val highscore: LiveData<Score>
        get() = _highscore

    fun fetchData() {
        mAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance().reference

        fun currentReference(): DatabaseReference =
            mDatabase.child("score").child(mAuth.currentUser!!.uid)

        currentReference().addListenerForSingleValueEvent(
            ValueListenerAdapter {
                val score = it.getValue(Score::class.java)
                _highscore.value = score
            }
        )
    }
}
