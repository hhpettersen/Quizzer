package com.example.quizzer.ui.start

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController

import com.example.quizzer.R
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.start_fragment.view.*

class StartFragment : Fragment() {

    var stringOut2: String? = null
    var highScore: Int? = 0
    var numGames: Int? = 0

    private val viewModel: StartViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.start_fragment, container, false)

        view.startGameButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_startFragment_to_questionsFragment,
                bundleOf("highscore" to highScore, "numGames" to numGames)
            )
        }

        view.logoutButton.setOnClickListener {
            AuthUI.getInstance().signOut(requireContext())
            findNavController().navigate(
                R.id.action_startFragment_to_loginFragment
            )
        }

        welcomeMessage()
        view.welcomeText.text = welcomeMessage()
        highScoreMessage(view)

        return view
    }



    private fun welcomeMessage(): String {
        val userName = FirebaseAuth.getInstance().currentUser?.displayName
        return "Hello, $userName! Good Luck on your quiz!"
    }

    private fun highScoreMessage(view: View) {
        viewModel.fetchData()

//        var highScore: Int? = 0
        var stringOut: String? = "123"

        viewModel.gameInfo.observe(viewLifecycleOwner, Observer {
            highScore = it.record
            numGames = it.numGames

            println(it)

            stringOut = "Your current saved highscore is $highScore"
            if(stringOut != null) {
                view.highScoreText.text = stringOut
            } else {
                view.highScoreText.visibility = View.GONE
            }
        })
    }
}
