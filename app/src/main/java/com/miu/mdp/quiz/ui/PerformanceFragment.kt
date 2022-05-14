package com.miu.mdp.quiz.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.miu.mdp.quiz.R
import com.miu.mdp.quiz.databinding.FragmentPerformanceBinding
import com.miu.mdp.quiz.ui.adapter.PerformanceAdapter

class PerformanceFragment : BaseFragment<FragmentPerformanceBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPerformanceBinding
        get() = FragmentPerformanceBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTitle("Current Performance")

        with(binding) {
            viewModel?.getUserPerformance()?.observe(viewLifecycleOwner) { result ->
                performanceResultList.layoutManager = LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.VERTICAL, false
                )
                performanceResultList.adapter = PerformanceAdapter(result)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.logout -> {
                viewModel?.logout()
                findNavController().navigate(PerformanceFragmentDirections.actionPerformanceFragmentToLoginFragment())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
