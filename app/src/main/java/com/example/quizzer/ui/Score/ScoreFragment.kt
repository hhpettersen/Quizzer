package com.example.quizzer.ui.Score

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

import com.example.quizzer.R
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

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ScoreViewModel::class.java)


    }

    fun setScoreText(view: View) {
        view.scoreText.text = score.toString()
    }

}
