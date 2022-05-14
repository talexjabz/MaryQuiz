package com.miu.mdp.quiz.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.miu.mdp.quiz.MainActivity
import com.miu.mdp.quiz.R
import com.miu.mdp.quiz.ui.view_model.MainViewModel

abstract class BaseFragment<B: ViewBinding>: Fragment() {
    private var _binding: ViewBinding? = null
    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> B

    var viewModel: MainViewModel? = null

    @Suppress("UNCHECKED_CAST")
    protected val binding: B
        get() = _binding as B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = bindingInflater.invoke(inflater, container, false)
        viewModel = (requireActivity() as MainActivity).mainViewModel
        return requireNotNull(_binding).root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
