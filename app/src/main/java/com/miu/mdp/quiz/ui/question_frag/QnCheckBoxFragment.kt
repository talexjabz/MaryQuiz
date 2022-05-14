package com.miu.mdp.quiz.ui.question_frag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.miu.mdp.quiz.databinding.FragmentQnCheckBoxBinding
import com.miu.mdp.quiz.entity.Question
import com.miu.mdp.quiz.ui.BaseFragment

class QnCheckBoxFragment : BaseFragment<FragmentQnCheckBoxBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentQnCheckBoxBinding
        get() = FragmentQnCheckBoxBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val question = arguments?.let {
            it.getSerializable("question") as Question
        }
        with(binding) {
            questionText.text = question?.question
            val options = question?.checkAnswer?.options
            opt1.text = options?.get(0)
            opt2.text = options?.get(1)
            opt3.text = options?.get(2)
            opt4.text = options?.get(3)

            val position = arguments?.getInt("position")

            viewModel?.selectedNext?.observe(viewLifecycleOwner) { pos ->
                if (pos == position) {

                    val answerInput = getCheckedBoxes(binding)
                    viewModel?.didAnswer(answerInput.isNotEmpty()) //Communicate to parent fragment, if question is answered

                    if (answerInput.isNotEmpty()) {//Update score.
                        viewModel?.updateScore(
                            position!!,
                            question?.checkAnswer?.computeAnswer(answerInput)!!
                        )
                    }
                }
            }
        }
    }

    private fun getCheckedBoxes(binding: FragmentQnCheckBoxBinding): List<Int> {
        val resultsList = mutableSetOf<Int>()
        with(binding) {
            if (opt1.isChecked) resultsList.add(0) else resultsList.remove(0)
            if (opt2.isChecked) resultsList.add(1) else resultsList.remove(1)
            if (opt3.isChecked) resultsList.add(2) else resultsList.remove(2)
            if (opt4.isChecked) resultsList.add(3) else resultsList.remove(3)
        }
        return resultsList.toList()
    }
}
