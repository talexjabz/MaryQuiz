package com.miu.mdp.quiz.ui.question_frag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.miu.mdp.quiz.databinding.FragmentQnRadioBinding
import com.miu.mdp.quiz.domain.Question
import com.miu.mdp.quiz.domain.QuestionAnswerHistory
import com.miu.mdp.quiz.ui.BaseFragment

class QnRadioFragment : BaseFragment<FragmentQnRadioBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentQnRadioBinding
        get() = FragmentQnRadioBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val question = arguments?.let {
            it.getSerializable("question") as Question
        }

        with(binding) {
            questionText.text = question?.question
            val options = question?.radioAnswer?.options
            opt1.text = options?.get(0)
            opt2.text = options?.get(1)
            opt3.text = options?.get(2)
            opt4.text = options?.get(3)

            val position = arguments?.getInt("position")

            viewModel?.selectedNext?.observe(viewLifecycleOwner) { pos ->
                if (pos == position) {

                    val radioButtonId = parentRadioGroup.checkedRadioButtonId
                    val radioButton = parentRadioGroup.findViewById<View>(radioButtonId)
                    val childId = parentRadioGroup.indexOfChild(radioButton)

                    viewModel?.didAnswer(radioButtonId != -1) //Communicate to parent fragment, if question is answered

                    if (radioButtonId != -1) {//Update score.
                        viewModel?.updateScore(
                            position!!,
                            question?.radioAnswer?.computeAnswer(childId)!!,
                            QuestionAnswerHistory(
                                question = question.question,
                                userAnswer = Gson().toJson(question.radioAnswer.options[childId]),
                                correctAnswer = Gson().toJson(question.radioAnswer.correct)
                            )
                        )
                    }
                }
            }
        }
    }
}
