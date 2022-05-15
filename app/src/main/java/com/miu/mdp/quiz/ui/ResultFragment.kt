package com.miu.mdp.quiz.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.miu.mdp.quiz.R
import com.miu.mdp.quiz.databinding.FragmentResultBinding

class ResultFragment : BaseFragment<FragmentResultBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentResultBinding
        get() = FragmentResultBinding::inflate

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTitle("Results")

        viewModel?.didCompleteQuiz()?.observe(viewLifecycleOwner) { result ->
            with(binding) {
                val overAll = (result.correct)*1f / (result.correct + result.wrong)*1f * 100
                ratingBar.rating = result.correct * 1f
                correctCount.text = result.correct.toString()
                wrongCount.text = result.wrong.toString()
                resultCount.text = "$overAll%"

                Glide
                    .with(this@ResultFragment)
                    .load("https://cliply.co/wp-content/uploads/2019/08/371908020_CONFETTI_400px.gif")
                    .centerInside()
                    .into(completeGif)

                btnViewPerformance.setOnClickListener {
                    findNavController().navigate(ResultFragmentDirections.actionResultFragmentToPerformanceFragment())
                }
                btnTryAgain.setOnClickListener {
                    findNavController().navigate(ResultFragmentDirections.actionResultFragmentToScanRoomFragment())
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.results_menu, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.logout).isVisible = false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.share -> {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, """
                        Just finished the Quiz daily challenge
                        
                        Checkout my scores!
                        Correct: ${binding.correctCount.text}
                        Wrong: ${binding.wrongCount.text}
                        
                        Result: ${binding.resultCount.text}
                        
                        Overall Rating. ${binding.ratingBar.rating} star
                        
                    """.trimIndent())
                    type = "text/plain"
                }
                startActivity(Intent.createChooser(intent, null))
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
