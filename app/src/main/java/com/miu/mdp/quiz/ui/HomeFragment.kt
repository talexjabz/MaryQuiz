package com.miu.mdp.quiz.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.miu.mdp.quiz.R
import com.miu.mdp.quiz.databinding.FragmentHomeBinding
import com.miu.mdp.quiz.databinding.FragmentScanRoomBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val name = (viewModel?.currentUser?.name)?.capitalize()
        setTitle("Welcome to the quiz $name")

        with(binding) {
            userNameTextView.text = String.format(resources.getString(R.string.hello), name)
            viewResultsBtn.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToPerformanceFragment())
            }

            takeQuizBtn.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToScanRoomFragment())
            }
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
