package com.example.tbcworks.domain

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.tbcworks.data.dataStore.TokenDataStore
import com.example.tbcworks.databinding.FragmentSplashBinding
import com.example.tbcworks.presentation.BaseFragment
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SplashFragment : BaseFragment<FragmentSplashBinding>() {
    private val tokenDataStore by lazy { TokenDataStore(requireContext()) }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSplashBinding {
        return FragmentSplashBinding.inflate(inflater, container, false)
    }

    override fun bind() {
        super.bind()
        lifecycleScope.launch {
            val token = tokenDataStore.getToken().first()
            val email = tokenDataStore.getEmail().first() ?: ""
            if (!token.isNullOrEmpty()) {
                findNavController().navigate(
                    SplashFragmentDirections.actionSplashFragmentToHomeFragment(email)
                )
            } else {
                findNavController().navigate(
                    SplashFragmentDirections.actionSplashFragmentToLoginFragment()
                )
            }
        }
    }


}