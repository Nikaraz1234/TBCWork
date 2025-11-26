package com.example.tbcworks.presentation.screens.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.tbcworks.databinding.FragmentRegisterBinding
import com.example.tbcworks.presentation.common.BaseFragment
import com.example.tbcworks.presentation.extensions.SnackBarHelper.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {
    private val viewModel: RegisterViewModel by viewModels()

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
            viewModel.onEvent(RegisterEvent.OnRegister(email, password, repeatPassword))
        }

    }


    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.sideEffect.collect { event ->
                    when (event) {
                        is RegisterSideEffect.ToLogin -> {

                            binding.root.showSnackBar(SUCCESS_MESSAGE)
                            val bundle = Bundle().apply {
                                putString(EMAIL, event.username)
                                putString(PASSWORD, event.password)
                            }
                            parentFragmentManager.setFragmentResult(REGISTER_KEY, bundle)
                            findNavController().popBackStack()
                        }

                        is RegisterSideEffect.ShowMessage -> binding.root.showSnackBar(event.message)
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