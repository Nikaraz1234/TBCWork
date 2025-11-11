package com.example.tbcworks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.tbcworks.common.auth.AuthUiState
import com.example.tbcworks.common.auth.AuthViewModel
import com.example.tbcworks.databinding.FragmentDashboardBinding
import com.example.tbcworks.extensions.SnackBarHelper.showSnackBar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class DashboardFragment : BaseFragment<FragmentDashboardBinding>() {
    private val viewModel: AuthViewModel by viewModels()

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDashboardBinding {
        return FragmentDashboardBinding.inflate(inflater, container, false)
    }


    override fun bind() {
        observe()
        handleArgs()
    }



    override fun listeners() = with(binding) {
        btnRegister.setOnClickListener {
            findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToRegisterFragment())
        }
        btnLogin.setOnClickListener {
            findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToLoginFragment())
        }
    }

    private fun observe()  = with(binding){
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiState.collectLatest { state ->
                    when(state) {
                        is AuthUiState.ShowMessage -> root.showSnackBar(state.message)
                        is AuthUiState.LoginSuccess -> {
                            root.showSnackBar("Login successful!")
                        }
                        is AuthUiState.RegisterSuccess -> {
                            root.showSnackBar("Register successful!")
                            arguments?.clear()
                        }
                        else -> {}
                    }
                }
            }
        }
    }

    private fun handleArgs() {
        val args = arguments?.let { DashboardFragmentArgs.fromBundle(it) }
        val username = args?.username
        val password = args?.password
        val email = args?.email

        if (!username.isNullOrBlank() && !password.isNullOrBlank() && !email.isNullOrBlank()) {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.register(username, password, email)
            }
        } else if (!username.isNullOrBlank() && !password.isNullOrBlank()) {
            viewModel.login(username, password)
        }
    }




}