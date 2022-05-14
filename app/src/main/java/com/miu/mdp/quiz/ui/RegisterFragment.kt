package com.miu.mdp.quiz.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.miu.mdp.quiz.MainActivity
import com.miu.mdp.quiz.R
import com.miu.mdp.quiz.databinding.FragmentRegisterBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentRegisterBinding
        get() = FragmentRegisterBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle("Register")

        with(binding) {
            registerButton.setOnClickListener {
                val username = nameInput.text.toString()
                val email = emailInput.text.toString()

                CoroutineScope(Dispatchers.Main).launch {
                    val userId = viewModel?.register(username, email)
                    dialog("Your user id is $userId. Save it because you'll never see it again!",
                    "New User ID")
                }
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }
}

fun Fragment.setTitle(title: String) = (activity as MainActivity).setActionBarTitle(title)
fun Fragment.toast(message: String) = Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
fun Fragment.dialog(message: String, title: String? = null, action: (() -> Unit)?=null) {
    AlertDialog
        .Builder(requireContext())
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(resources.getString(R.string.okay)) { _, _ ->
            action?.invoke()
        }.show()
}
