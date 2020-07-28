package com.example.quizzer.ui.Start

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

import com.example.quizzer.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.start_fragment.view.*

class StartFragment : Fragment() {

    companion object {
        fun newInstance() = StartFragment()
    }

    private lateinit var viewModel: StartViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.start_fragment, container, false)
        view.startGameButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_startFragment_to_questionsFragment
            )
        }
        view.welcomeText.text = welcomeMessage()
        return view
    }

    private fun welcomeMessage(): String {
        val userName = FirebaseAuth.getInstance().currentUser?.displayName
        return "Hello, $userName! Good Luck on your quiz!"
    }
}
