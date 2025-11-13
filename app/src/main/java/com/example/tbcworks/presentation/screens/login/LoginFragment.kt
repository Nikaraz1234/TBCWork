package com.example.tbcworks.presentation.screens.login

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.tbcworks.data.auth.AuthUiState
import com.example.tbcworks.data.dataStore.saveToken
import com.example.tbcworks.presentation.BaseFragment
import com.example.tbcworks.databinding.FragmentLoginBinding
import com.example.tbcworks.presentation.screens.AuthViewModelFactory
import com.example.tbcworks.presentation.common.extensions.SnackBarHelper.showSnackBar
import kotlinx.coroutines.launch

class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    private val viewModel: LoginViewModel by viewModels {
        AuthViewModelFactory()
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLoginBinding {
        return FragmentLoginBinding.inflate(inflater, container, false)
    }

    override fun listeners() = with(binding) {
        btnLogin.setOnClickListener {
            login()
        }
    }

    override fun bind() {
        observe()
    }

    private fun login() = with(binding) {
        val username = etUsername.text.toString().trim()
        val password = etPassword.text.toString().trim()

        if (validateLoginInputs(username, password)) {
            viewLifecycleOwner.lifecycleScope.launch {
                val result = viewModel.login(username, password)
                result.fold(
                    onSuccess = { response ->
                        saveToken(requireContext(), response.token!!)
                        root.showSnackBar(LOGIN_SUCCESS_MESSAGE)
                        findNavController().navigate(
                            LoginFragmentDirections.actionLoginFragmentToHomeFragment(username)
                        )
                    },
                    onFailure = {
                        root.showSnackBar(LOGIN_FAILED_MESSAGE)
                    }
                )
            }
        }
    }

    private fun observe() = with(binding) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when (state) {
                    is AuthUiState.ShowMessage -> root.showSnackBar(state.message)
                    is AuthUiState.LoginSuccess -> {
                        root.showSnackBar(LOGIN_SUCCESS_MESSAGE)
                        findNavController().navigate(
                            LoginFragmentDirections.actionLoginFragmentToHomeFragment(
                                username = etUsername.text.toString()
                            )
                        )
                    }
                    else -> {}
                }
            }
        }
    }

    private fun validateLoginInputs(username: String, password: String): Boolean {
        with(binding) {
            return if (username.isBlank() || password.isBlank()) {
                root.showSnackBar(FILL_ALL_FIELDS_MESSAGE)
                false
            } else {
                true
            }
        }
    }

    companion object {
        private const val LOGIN_SUCCESS_MESSAGE = "Login successful!"
        private const val LOGIN_FAILED_MESSAGE = "Login failed"
        private const val FILL_ALL_FIELDS_MESSAGE = "Please fill all the fields"
    }
}
