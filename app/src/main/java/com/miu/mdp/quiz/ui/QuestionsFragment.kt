package com.miu.mdp.quiz.ui

import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.miu.mdp.quiz.R
import com.miu.mdp.quiz.databinding.FragmentQuestionsBinding
import com.miu.mdp.quiz.ui.adapter.QuestionAdapter

class QuestionsFragment : BaseFragment<FragmentQuestionsBinding>() {
    private var questionSize = 0
    private lateinit var adapter: QuestionAdapter

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentQuestionsBinding
        get() = FragmentQuestionsBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTitle("Goodluck ${viewModel?.currentUser?.name}!")

        viewModel?.getAllQuestions()?.observe(viewLifecycleOwner) { questionsList ->
            adapter = QuestionAdapter(this, questionsList.shuffled())
            binding.viewPager.adapter = adapter
            questionSize = questionsList.size
            setupListeners()
        }

    }

    private fun setupListeners() {
        with(binding) {
            viewPager.setCurrentItem(0, true)

            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    prevButton.isVisible = position != 0
                }
            })

            prevButton.setOnClickListener {
                viewPager.setCurrentItem(viewPager.currentItem - 1, true)
            }
            skip.setOnClickListener {
                if (viewPager.currentItem == questionSize - 1) {
                    findNavController()
                        .navigate(QuestionsFragmentDirections.actionQuestionsFragmentToResultFragment())
                }
                else viewPager.setCurrentItem(viewPager.currentItem + 1, true)
            }
            nextButton.setOnClickListener {
                viewModel?.didSelectNext(viewPager.currentItem)
            }

            viewModel?.onQnAnswered?.observe(viewLifecycleOwner) { answered ->
                if (answered) {
                    if (viewPager.currentItem == questionSize - 1) {
                        findNavController()
                            .navigate(QuestionsFragmentDirections.actionQuestionsFragmentToResultFragment())
                    } else viewPager.setCurrentItem(viewPager.currentItem + 1, true)
                } else toast("You can't leave the question blank. Skip it instead.")
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.quiz_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout -> {
                viewModel?.logout()
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
                true
            }
            R.id.exit -> {
                viewModel?.clearQuestions()
                findNavController().navigate(QuestionsFragmentDirections.actionQuestionsFragmentToHomeFragment())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
