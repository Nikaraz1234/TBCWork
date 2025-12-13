package com.example.tbcworks.presentation.screens.signup

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
                SignUpSideEffect.NavigateToHome -> {  }
                SignUpSideEffect.NavigateToLogin -> { findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToLoginFragment()) }
                is SignUpSideEffect.ShowError -> { binding.root.showSnackBar(effect.message) }
            }
        }
    }
}
