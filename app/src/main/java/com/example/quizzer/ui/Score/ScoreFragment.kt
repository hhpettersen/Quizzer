package com.example.quizzer.ui.Score

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController

import com.example.quizzer.R
import com.example.quizzer.data.entities.Score
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.score_fragment.view.*

class ScoreFragment : Fragment() {

    companion object {
        fun newInstance() = ScoreFragment()
    }

    private lateinit var viewModel: ScoreViewModel
    private var score: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.score_fragment, container, false)
        score = arguments?.getInt("score")
        setScoreText(view)

        view.newGameButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_scoreFragment_to_startFragment
            )
        }

        view.submitScoreButton.setOnClickListener {
            submitScoreToFirebase(score)
        }

        return view
    }

    private fun submitScoreToFirebase(score: Int?) {
        val ref = FirebaseDatabase.getInstance().getReference("users")
        val checkIfExist = ref.push().key
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        val scoreNode = Score(1, score, "dato")

        if (checkIfExist != null) {
            if (uid != null) {
                ref.child(uid).child("game").setValue(scoreNode).addOnCompleteListener {
                    Toast.makeText(context, "Saved", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    fun setScoreText(view: View) {
        view.scoreSum.text = score.toString()
    }

}
