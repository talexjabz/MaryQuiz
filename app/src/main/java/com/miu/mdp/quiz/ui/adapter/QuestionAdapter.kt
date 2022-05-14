package com.miu.mdp.quiz.ui.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.miu.mdp.quiz.entity.Question
import com.miu.mdp.quiz.ui.question_frag.QnCheckBoxFragment
import com.miu.mdp.quiz.ui.question_frag.QnRadioFragment
import com.miu.mdp.quiz.ui.question_frag.QnTextFragment

class QuestionAdapter(
    fragment: Fragment,
    private val questionsList: List<Question>
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return questionsList.size
    }

    override fun createFragment(position: Int): Fragment {
        return generateFragment(position)
    }

    private fun generateFragment(position: Int): Fragment {
        val fragment: Fragment = when(questionsList[position].type) {
            "check" -> QnCheckBoxFragment()
            "radio" -> QnRadioFragment()
            "text" -> QnTextFragment()
            else -> throw IllegalStateException()
        }
        fragment.apply {
            arguments = Bundle().apply {
                putSerializable("question", questionsList[position])
                putInt("position", position)
            }
        }
        return fragment
    }

}
