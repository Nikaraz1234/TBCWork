package com.example.tbcworks.presentation.screens.register

import android.util.Patterns
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.tbcworks.data.auth.AuthUiState
import com.example.tbcworks.presentation.BaseFragment
import com.example.tbcworks.databinding.FragmentRegisterBinding
import com.example.tbcworks.presentation.screens.AuthViewModelFactory
import com.example.tbcworks.presentation.common.extensions.SnackBarHelper.showSnackBar
import kotlinx.coroutines.launch
import kotlin.getValue

class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {
    private val viewModel: RegisterViewModel by viewModels {
        AuthViewModelFactory()
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegisterBinding {
        return FragmentRegisterBinding.inflate(inflater, container, false)
    }

    override fun listeners() = with(binding) {
        btnRegister.setOnClickListener {
            register()
        }
    }

    override fun bind() {
        observe()
    }

    private fun register() = with(binding) {
        btnRegister.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val email = etEmail.text.toString().trim()

            if (validateInputs(username, password, email)) {
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.register(username, password, email)
                }

            }
        }
    }

    private fun observe() = with(binding) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when (state) {
                    is AuthUiState.ShowMessage -> root.showSnackBar(state.message)
                    is AuthUiState.RegisterSuccess -> {
                        root.showSnackBar(SUCCESS_MESSAGE)
                        findNavController().navigate(
                            RegisterFragmentDirections.actionRegisterFragmentToDashboardFragment()
                        )
                    }

                    else -> {}
                }
            }
        }
    }

    private fun validateInputs(username: String, password: String, email: String): Boolean {
        with(binding) {
            return if (username.isBlank() || password.isBlank() || email.isBlank()) {
                root.showSnackBar(FILL_ALL_FIELDS_MESSAGE)
                false
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                root.showSnackBar(INVALID_EMAIL_MESSAGE)
                false
            } else {
                true
            }
        }
    }

    companion object {
        private const val SUCCESS_MESSAGE = "Registration successful!"
        private const val FILL_ALL_FIELDS_MESSAGE = "Please fill all the fields"
        private const val INVALID_EMAIL_MESSAGE = "Invalid Email"
    }
}