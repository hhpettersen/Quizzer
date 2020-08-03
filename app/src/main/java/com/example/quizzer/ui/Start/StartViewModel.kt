package com.example.quizzer.ui.Start

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quizzer.data.entities.Score
import com.example.quizzer.data.entities.User
import com.example.quizzer.utils.ValueListenerAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class StartViewModel : ViewModel() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDatabase: DatabaseReference

    private val _highscore = MutableLiveData<Int>()
    val highscore: LiveData<Int>
        get() = _highscore

    fun fetchData() {
        mAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance().reference

        fun currentReference(): DatabaseReference =
            mDatabase.child("users").child(mAuth.currentUser!!.uid)

        currentReference().addListenerForSingleValueEvent(
            ValueListenerAdapter {
                val user = it.getValue(User::class.java)
                if(user != null) {
                    _highscore.value = user.game?.record
                } else {
                    _highscore.value = 0
                }
            }
        )
    }
}
