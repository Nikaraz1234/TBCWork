package com.example.tbcworks.presentation.screens.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.tbcworks.presentation.BaseFragment
import com.example.tbcworks.databinding.FragmentDashboardBinding


class DashboardFragment : BaseFragment<FragmentDashboardBinding>() {

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDashboardBinding {
        return FragmentDashboardBinding.inflate(inflater, container, false)
    }

    override fun listeners() = with(binding) {
        btnRegister.setOnClickListener {
            findNavController().navigate(DashboardFragmentDirections.Companion.actionDashboardFragmentToRegisterFragment())
        }
        btnLogin.setOnClickListener {
            findNavController().navigate(DashboardFragmentDirections.Companion.actionDashboardFragmentToLoginFragment())
        }
    }






}