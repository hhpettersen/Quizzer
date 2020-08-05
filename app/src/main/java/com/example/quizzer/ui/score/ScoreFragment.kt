package com.example.quizzer.ui.score

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController

import com.example.quizzer.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.score_fragment.view.*

class ScoreFragment : Fragment() {

    private lateinit var viewModel: ScoreViewModel
    private var score: Int = 0
    private var highScore: Int = 0
    private var numGames: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.score_fragment, container, false)

        score = arguments?.getInt("score")!!
        highScore = arguments?.getInt("highScore")!!
        numGames = arguments?.getInt("numGames")!!

        setScoreText(view)

        submitScoreToFirebase(score, numGames)

        view.newGameButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_scoreFragment_to_startFragment
            )
        }

        return view
    }

    private fun submitScoreToFirebase(score: Int, numGames: Int) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        var numGamesInc = numGames.plus(1)
        var gameReference = uid?.let { FirebaseDatabase.getInstance().getReference("users").child(it).child("game") }


            if (uid != null) {
                gameReference?.child("numGames")?.setValue(numGamesInc)?.addOnCompleteListener {
                    Toast.makeText(context, "Number of games increased", Toast.LENGTH_LONG).show()
                }
            }


        if(score > highScore) {
            newHighScoreText(view)
            gameReference?.child("record")?.setValue(score)?.addOnCompleteListener {
                Toast.makeText(context, "New highscore saved", Toast.LENGTH_LONG).show()
            }
            gameReference?.child("date")?.setValue("datohehe")
        }
    }

    private fun setScoreText(view: View) {
        view.scoreSum.text = score.toString()
    }


    private fun newHighScoreText(view: View?) {
        if (view != null) {
            view.scoreText.text = "You achieved a new highscore!"
        }
    }
}
