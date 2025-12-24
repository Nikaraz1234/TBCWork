package com.example.tbcworks.presentation.screen.sign_in

import androidx.core.widget.doAfterTextChanged
import com.example.tbcworks.databinding.FragmentSignInBinding
import com.example.tbcworks.presentation.common.BaseFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tbcworks.presentation.extension.collectFlow
import com.example.tbcworks.presentation.extension.collectStateFlow
import com.example.tbcworks.presentation.extension.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSignInBinding>(
    FragmentSignInBinding::inflate
) {

    private val viewModel: SignInViewModel by viewModels()

    override fun bind() {
        setup()
    }
    private fun setup() {
        setupListeners()
        observeState()
        observeSideEffects()
    }

    private fun setupListeners() = with(binding) {

        cbRemember.setOnCheckedChangeListener { _, checked ->
            viewModel.onEvent(SignInContract.Event.RememberMeChanged(checked))
        }

        btnSignIn.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            viewModel.onEvent(SignInContract.Event.SetEmail(email))
            viewModel.onEvent(SignInContract.Event.SetPassword(password))
            viewModel.onEvent(SignInContract.Event.SignInClicked)
        }
        tvBtnSignUp.setOnClickListener {
            viewModel.onEvent(SignInContract.Event.SignUpClicked)
        }
    }

    private fun observeState() = with(binding){
        collectStateFlow(viewModel.uiState) { state ->
            etEmail.error = state.emailError
            etPassword.error = state.passwordError
            cbRemember.isChecked = state.rememberMe
        }
    }

    private fun observeSideEffects() {
        collectFlow(viewModel.sideEffect) { effect ->
            when (effect) {
                SignInContract.SideEffect.NavigateToHome -> {
                    findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToEventHubFragment())
                }
                SignInContract.SideEffect.NavigateToForgotPassword -> {
                    // navigate to forgot password
                }
                SignInContract.SideEffect.NavigateToSignUp -> {
                    findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToSignUpFragment())
                }
                is SignInContract.SideEffect.ShowError -> {
                }
            }
        }
    }
}
