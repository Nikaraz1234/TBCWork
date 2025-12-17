package com.example.tbcworks.presentation.screens.login

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tbcworks.databinding.FragmentLoginBinding
import com.example.tbcworks.presentation.common.BaseFragment
import com.example.tbcworks.presentation.common.FragmentKeys
import com.example.tbcworks.presentation.extension.SnackBarHelper.showSnackBar
import com.example.tbcworks.presentation.extension.collectFlow
import com.example.tbcworks.presentation.extension.collectStateFlow
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(
    FragmentLoginBinding::inflate
) {
    private val viewModel: LoginViewModel by viewModels()

    override fun listeners() = with(binding){
        tvToSignUp.setOnClickListener {
            viewModel.onEvent(LoginEvent.OnSignUpClick)
        }
        btnLogin.setOnClickListener {
            login()
        }
    }
    private fun login() = with(binding){
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()
        viewModel.onEvent(LoginEvent.OnLoginClick(email, password))
    }

    override fun bind() {
        observers()
        setupResultListener()
    }

    private fun observers() {
        collectState()
        collectSideEffects()
    }

    private fun collectState() {
        collectStateFlow(viewModel.uiState) { state ->
            binding.progressBar.isVisible = state.isLoading
        }
    }

    private fun collectSideEffects() = with(binding){
        collectFlow(viewModel.sideEffect) { effect ->
            when (effect) {
                LoginSideEffect.NavigateToHome -> {
                    findNavController().navigate(
                        LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                    )
                }

                LoginSideEffect.NavigateToSignUp -> {
                    findNavController().navigate(
                        LoginFragmentDirections
                            .actionLoginFragmentToSignUpFragment()
                    )
                }

                is LoginSideEffect.ShowError -> {
                    root.showSnackBar(effect.message)
                }

                LoginSideEffect.ShowLoading -> {
                    progressBar.isVisible = true
                }

                LoginSideEffect.HideLoading -> {
                    progressBar.isVisible = false
                }
            }
        }
    }

    private fun setupResultListener() {
        parentFragmentManager.setFragmentResultListener(
            FragmentKeys.SIGNUP_RESULT,
            viewLifecycleOwner
        ) { _, bundle ->
            val email = bundle.getString(FragmentKeys.EMAIL, "")
            val password = bundle.getString(FragmentKeys.PASSWORD, "")

            binding.etEmail.setText(email)
            binding.etPassword.setText(password)
        }
    }
}