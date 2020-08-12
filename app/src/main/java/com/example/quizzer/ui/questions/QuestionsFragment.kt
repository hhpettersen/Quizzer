package com.example.quizzer.ui.questions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.quizzer.R
import com.example.quizzer.data.datarepo.models.QuestionBool
import com.example.quizzer.data.datarepo.network.Api
import com.example.quizzer.data.datarepo.repository.Repository
import com.example.quizzer.utils.stringFormatter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.questions_fragment.view.*


@AndroidEntryPoint
class QuestionsFragment : Fragment() {

    private lateinit var viewModel: QuestionsViewModel
    private lateinit var factory: QuestionsViewModelFactory

    private var score: Int = 0
    private var highScore: Int? = null
    private var numGames: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.questions_fragment, container, false)

        setupViewModel()
        setupObservers(view)

        highScore = arguments?.getInt("highScore")
        numGames = arguments?.getInt("numGames")

        return view
    }

    private fun setupViewModel() {
        val api  = Api()
        val repository = Repository(api)
        factory = QuestionsViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(QuestionsViewModel::class.java)
        viewModel.getQuestionsBool()
    }

//    private fun setupObservers(view: View) {
//        viewModel.questions.observe(viewLifecycleOwner, Observer {
//            when (it.status) {
//                Resource.Status.SUCCESS -> {
//                    if (!it.data.isNullOrEmpty())
//                        bindQuestions(view, it.data)
//                }
//                Resource.Status.ERROR ->
//                    Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
//
//                Resource.Status.LOADING ->
//                    Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
//            }
//        })
//    }
        private fun setupObservers(view: View) {
            viewModel.q.observe(viewLifecycleOwner, Observer {
                if(!it.results.isNullOrEmpty()) {
                    bindQuestions(view, it.results)
                }
        })
    }

    private fun bindQuestions(view: View, questions: List<QuestionBool>) {
        viewModel.score.observe(viewLifecycleOwner, Observer {
            println(it)
            score = it
            view.scoreValue.text = getString(R.string.scoreValueString, it.toString())
        })

        viewModel.questionIndex.observe(viewLifecycleOwner, Observer {
            if(it >= 5) {
                try {
                    findNavController().navigate(
                        R.id.action_questionsFragment_to_scoreFragment,
                        bundleOf("score" to score, "highScore" to highScore, "numGames" to numGames)
                    )
                    } catch (e: Exception) {
                    //TODO
                }
            } else {

                view.questionText.text = stringFormatter(questions[it].question)
                val correctAnswer = questions[it].correct_answer

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
