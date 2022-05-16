package com.miu.mdp.quiz.ui

import android.os.Bundle
import android.view.*
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.miu.mdp.quiz.databinding.FragmentLoginBinding


class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLoginBinding
        get() = FragmentLoginBinding::inflate

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        if (viewModel?.checkLoggedIn() == true)
            findNavController()
                .navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTitle("Sign in")

        with(binding) {
            floatingActionButton.setOnClickListener {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
            }

            signIn.setOnClickListener {
                val userId = userIdTextInput.text.toString()
                if (userId.isEmpty()) toast("Enter user id to proceed")
                else {
                    viewModel?.login(userId)?.observe(viewLifecycleOwner) { isLoggedIn ->
                        if (isLoggedIn) {
                            findNavController()
                                .navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
                        } else toast("Enter valid user id to proceed")
                    }
                }
            }

            Glide
                .with(this@LoginFragment)
                .load("https://is3-ssl.mzstatic.com/image/thumb/Purple60/v4/50/e8/7c/50e87cd6-2f38-138e-9c33-866b73ac6e95/source/512x512bb.jpg")
                .into(quizImage)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }

}

