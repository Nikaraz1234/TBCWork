package com.example.tbcworks.presentation.screens.login

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.tbcworks.presentation.common.BaseFragment
import com.example.tbcworks.databinding.FragmentLoginBinding
import com.example.tbcworks.presentation.extensions.SnackBarHelper.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    private val viewModel: LoginViewModel by viewModels()

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
        btnRegister.setOnClickListener {
            viewModel.onEvent(LoginEvent.OnRegister)
        }
    }

    override fun bind() {
        observe()
        setResultListener()
    }

    private fun login() = with(binding) {
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()
        val rememberMe = cbRememberMe.isChecked

        viewModel.onEvent(LoginEvent.OnLogin(email, password, rememberMe))
    }


    private fun setResultListener() {
        parentFragmentManager.setFragmentResultListener(
            REGISTER_KEY,
            viewLifecycleOwner
        ) { requestKey, bundle ->
            val username = bundle.getString(EMAIL)
            val password = bundle.getString(PASSWORD)

            username?.let { binding.etEmail.setText(it) }
            password?.let { binding.etPassword.setText(it) }
        }
    }


    private fun observe() = with(binding) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.sideEffect.collect { event ->
                    when (event) {
                        is LoginSideEffect.ToHome -> {
                            root.showSnackBar(LOGIN_SUCCESS_MESSAGE)
                            findNavController().navigate(
                                LoginFragmentDirections.actionLoginFragmentToHomeFragment(
                                    username = etEmail.text.toString()
                                )
                            )
                        }
                        is LoginSideEffect.ToRegister -> {
                            findNavController().navigate(
                                LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
                            )
                        }
                        is LoginSideEffect.ShowMessage -> {
                            root.showSnackBar(event.message)
                        }
                    }
                }
            }

        }
    }

    companion object {
        private const val LOGIN_SUCCESS_MESSAGE = "Login successful!"
        private const val EMAIL = "email"
        private const val PASSWORD = "password"
        private const val REGISTER_KEY = "register_key"
    }
}
