package com.miu.mdp.quiz.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.miu.mdp.quiz.R
import com.miu.mdp.quiz.databinding.FragmentPerformanceBinding
import com.miu.mdp.quiz.ui.adapter.PerformanceAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PerformanceFragment : BaseFragment<FragmentPerformanceBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPerformanceBinding
        get() = FragmentPerformanceBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTitle("Current Performance")

        with(binding) {
            viewModel?.getUserPerformance()?.observe(viewLifecycleOwner) { result ->
                if (result.isEmpty()) {
                    Glide.with(this@PerformanceFragment)
                        .load("https://i.pinimg.com/originals/ae/8a/c2/ae8ac2fa217d23aadcc913989fcc34a2.png")
                        .into(binding.emptyList)
                    binding.emptyList.isVisible = true
                } else {
                    binding.emptyList.isVisible = false
                }
            }

            viewModel?.getQuestionAnswerHistory()?.observe(viewLifecycleOwner) { history ->
                performanceResultList.layoutManager = LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.VERTICAL, false
                )
                performanceResultList.adapter = PerformanceAdapter(history)
            }

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout -> {
                viewModel?.logout()
                findNavController().navigate(PerformanceFragmentDirections.actionPerformanceFragmentToLoginFragment())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
