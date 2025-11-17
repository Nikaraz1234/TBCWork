package com.example.tbcworks.presentation.screens.register

import android.os.Bundle
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
        AuthViewModelFactory(requireContext())
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
        val password = etPassword.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val repeatPassword = etRepeatPassword.text.toString().trim()
        viewLifecycleOwner.lifecycleScope.launch {
            val success = viewModel.register(email, password, repeatPassword)
            if (success) {
                onRegisterSuccess(email, password)
            }
        }

    }

    private fun onRegisterSuccess(email: String, password: String) {
        val bundle = Bundle().apply {
            putString("email", email)
            putString("password", password)
        }
        parentFragmentManager.setFragmentResult("register_key", bundle)
        findNavController().popBackStack()
    }

    private fun observe() = with(binding) {
        observeUiState()
        observeNavigation()
    }
    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiEvent.collect { state ->
                when (state) {
                    is AuthUiState.ShowMessage -> binding.root.showSnackBar(state.message)
                    else -> {}
                }
            }
        }
    }

    private fun observeNavigation() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.navigationEvent.collect { event ->
                when (event) {
                    is RegisterViewModel.NavigationEvent.ToLogin -> {
                        binding.root.showSnackBar(SUCCESS_MESSAGE)
                        val bundle = Bundle().apply {
                            putString(EMAIL, event.username)
                            putString(PASSWORD, event.password)
                        }
                        parentFragmentManager.setFragmentResult(REGISTER_KEY, bundle)
                        findNavController().popBackStack()
                    }
                }
            }
        }
    }

    companion object {
        private const val SUCCESS_MESSAGE = "Registration successful!"
        private const val EMAIL = "email"
        private const val PASSWORD = "password"
        private const val REGISTER_KEY = "register_key"
    }
}