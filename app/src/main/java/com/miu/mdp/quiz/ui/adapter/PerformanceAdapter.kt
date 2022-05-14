package com.miu.mdp.quiz.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.miu.mdp.quiz.databinding.PerformanceItemBinding
import com.miu.mdp.quiz.entity.Result
import java.text.SimpleDateFormat
import java.util.*

class PerformanceAdapter(
    private val resultList: List<Result>
) : RecyclerView.Adapter<PerformanceAdapter.PerformanceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PerformanceViewHolder {
        val itemBinder =
            PerformanceItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PerformanceViewHolder(itemBinder)
    }

    override fun onBindViewHolder(holder: PerformanceViewHolder, position: Int) {
        holder.configure(resultList[position])
    }

    override fun getItemCount(): Int = resultList.size

    class PerformanceViewHolder(private val itemBinder: PerformanceItemBinding) : RecyclerView.ViewHolder(itemBinder.root) {
        @SuppressLint("SetTextI18n")
        fun configure(result: Result) {
            val simpleFormatter = SimpleDateFormat("MMM/dd/yyyy", Locale.ENGLISH)
            itemBinder.correct.text = "Correct ${result.correct}"
            itemBinder.wrong.text = "Wrong ${result.wrong}"
            itemBinder.date.text = "On ${simpleFormatter.format(result.date)}"
        }
    }
}
