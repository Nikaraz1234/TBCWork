package com.example.tbcworks

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.tbcworks.databinding.FragmentRegisterBinding
import com.example.tbcworks.extensions.SnackBarHelper.showSnackBar


class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegisterBinding {
        return FragmentRegisterBinding.inflate(inflater, container, false)
    }

    override fun listeners() = with(binding){
        btnRegister.setOnClickListener {
            register()
        }
    }

    private fun register() = with(binding){
        val username = etUsername.text.toString().trim()
        val password = etPassword.text.toString().trim()
        val email = etEmail.text.toString().trim()

        if(validateInputs(username, password, email)){
            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToDashboardFragment(
                username = username,
                password = password,
                email = email
            ))
        }
    }


    private fun validateInputs(username: String, password: String, email: String): Boolean {
        with(binding){
            return if (username.isBlank() || password.isBlank() || email.isBlank()) {
                root.showSnackBar("Please fill all the fields")
                false
            }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                root.showSnackBar("Invalid Email")
                false
            }else{
                true
            }
        }
    }

}