package com.example.tbcworks.presentation.screens.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tbcworks.data.dataStore.removeToken
import com.example.tbcworks.databinding.FragmentHomeBinding
import com.example.tbcworks.presentation.BaseFragment
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private val args by navArgs<HomeFragmentArgs>()

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun bind() {
        setUpUsername()
    }

    private fun setUpUsername() = with(binding){
        tvUsername.text = args.username
    }

    override fun listeners() = with(binding) {
        btnLogout.setOnClickListener {
            logout()
        }
    }

    private fun logout() = with(binding){
        lifecycleScope.launch {
            removeToken(requireContext())
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDashboardFragment())
        }
    }


}