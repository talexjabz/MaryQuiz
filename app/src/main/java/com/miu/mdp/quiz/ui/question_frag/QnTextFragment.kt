package com.miu.mdp.quiz.ui.question_frag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.miu.mdp.quiz.databinding.FragmentQnTextBinding
import com.miu.mdp.quiz.domain.Question
import com.miu.mdp.quiz.domain.QuestionAnswerHistory
import com.miu.mdp.quiz.ui.BaseFragment

class QnTextFragment : BaseFragment<FragmentQnTextBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentQnTextBinding
        get() = FragmentQnTextBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val question = arguments?.let {
            it.getSerializable("question") as Question
        }
        val position = arguments?.getInt("position")

        with(binding) {
            questionText.text = question?.question

            viewModel?.selectedNext?.observe(viewLifecycleOwner) { pos ->
                if (pos == position) {

                    val answerInput = answerInput.text.toString()
                    viewModel?.didAnswer(answerInput.isNotEmpty()) //Communicate to parent fragment, if question is answered

                    if (answerInput.isNotEmpty()) {//Update score.
                        viewModel?.updateScore(
                            position!!,
                            question?.textAnswer?.computeAnswer(answerInput)!!,
                            QuestionAnswerHistory(
                                question = question.question,
                                userAnswer = answerInput,
                                correctAnswer = Gson().toJson(question.textAnswer.correct)
                            )
                        )
                    }
                }
            }
        }
    }
}
