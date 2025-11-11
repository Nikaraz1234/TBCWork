package com.example.tbcworks

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.tbcworks.databinding.FragmentLoginBinding
import com.example.tbcworks.extensions.SnackBarHelper.showSnackBar


class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLoginBinding {
        return FragmentLoginBinding.inflate(inflater,container, false)
    }

    override fun listeners() = with(binding){
        btnLogin.setOnClickListener {
            login()

        }
    }

    private fun login() = with(binding){
        val username = etUsername.text.toString().trim()
        val password = etPassword.text.toString().trim()

        validateLoginInputs(username, password)
        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToDashboardFragment(
            username = username,
            password = password
        ))
    }

    private fun validateLoginInputs(username: String, password: String): String?{
        if (username.isBlank() || password.isBlank()) {
            binding.root.showSnackBar("Please fill all the fields")
        }
        return null
    }



}