package com.miu.mdp.quiz.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.miu.mdp.quiz.R
import com.miu.mdp.quiz.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val name = (viewModel?.currentUser?.name)?.capitalize()
        setTitle("Welcome to the quiz $name")

        with(binding) {
            viewResultsBtn.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToPerformanceFragment())
            }

            takeQuizBtn.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToQuestionsFragment())
            }
            Glide
                .with(this@HomeFragment)
                .load("https://is3-ssl.mzstatic.com/image/thumb/Purple60/v4/50/e8/7c/50e87cd6-2f38-138e-9c33-866b73ac6e95/source/512x512bb.jpg")
                .into(quizImage)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout -> {
                viewModel?.logout()
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
