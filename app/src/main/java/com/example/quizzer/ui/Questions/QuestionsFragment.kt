package com.example.quizzer.ui.Questions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.quizzer.R
import com.example.quizzer.data.entities.Question
import com.example.quizzer.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.questions_fragment.view.*


@AndroidEntryPoint
class QuestionsFragment : Fragment() {

    private val viewModel: QuestionsViewModel by viewModels()
    private var score: Int = 0
    private var numQuestions: Int = 0
    private var correctAnswer: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.questions_fragment, container, false)
        setupObservers(view)
        return view
    }

    private fun setupObservers(view: View) {
        viewModel.questions.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    if (!it.data.isNullOrEmpty())
                        bindQuestions(view, it.data)
                }
                Resource.Status.ERROR ->
                    Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()

                Resource.Status.LOADING ->
                    Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun bindQuestions(view: View, questions: List<Question>) {

        numQuestions = questions.size

        viewModel.score.observe(viewLifecycleOwner, Observer {
            score = it
            view.scoreValue.text = it.toString()
        })

        viewModel.questionIndex.observe(viewLifecycleOwner, Observer {
            // Ends quiz after 5 answered questions and deletes questions in db
            if(it >= 5) {
                viewModel.onEndQuiz()
                try {
                    findNavController().navigate(
                        R.id.action_questionsFragment_to_scoreFragment,
                        bundleOf("score" to score)
                    )
                    } catch (e: Exception) {
                    //TODO
                }
            } else {
                view.questionText.text = questions[it].question
                correctAnswer = questions[it].correct_answer

                if(correctAnswer) {
                    view.trueButton.setOnClickListener {
                        viewModel.onCorrectAnswer()
                    }
                    view.falseButton.setOnClickListener {
                        viewModel.onWrongAnswer()
                    }
                } else {
                    view.trueButton.setOnClickListener {
                        viewModel.onWrongAnswer()
                    }
                    view.falseButton.setOnClickListener {
                        viewModel.onCorrectAnswer()
                    }
                }
            }
        })
    }
}
