package com.example.tbcworks.presentation.screens.signup

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tbcworks.databinding.FragmentSignUpBinding
import com.example.tbcworks.presentation.common.BaseFragment
import com.example.tbcworks.presentation.extension.SnackBarHelper.showSnackBar
import com.example.tbcworks.presentation.extension.collectFlow
import com.example.tbcworks.presentation.extension.collectStateFlow
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>(
    FragmentSignUpBinding::inflate
) {

    private val viewModel: SignUpViewModel by viewModels()

    override fun listeners() = with(binding) {
        btnSignUp.setOnClickListener {
            signUp()
        }

        tvToLogin.setOnClickListener {
            viewModel.onEvent(SignUpEvent.OnLoginClick)
        }
    }

    private fun signUp() = with(binding){
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()
        viewModel.onEvent(SignUpEvent.OnSignUpClick(email, password))
    }

    override fun bind() {
        observers()
    }

    private fun observers() {
        collectStateFlow(viewModel.uiState) { state ->
            binding.progressBar.isVisible = state.isLoading
        }

        collectFlow(viewModel.sideEffect) { effect ->
            when (effect) {
                SignUpSideEffect.NavigateToHome -> {
                    val email = binding.etEmail.text.toString().trim()
                    val password = binding.etPassword.text.toString().trim()
                    sendCredentialsToLogin(email, password)
                }

                SignUpSideEffect.NavigateToLogin -> { findNavController().popBackStack() }
                is SignUpSideEffect.ShowError -> { binding.root.showSnackBar(effect.message) }
            }
        }
    }

    private fun sendCredentialsToLogin(email: String, password: String) {
        val bundle = Bundle().apply {
            putString("email", email)
            putString("password", password)
        }
        parentFragmentManager.setFragmentResult("signup_result", bundle)
        findNavController().popBackStack()
    }
}
