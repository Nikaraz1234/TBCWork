package com.example.tbcworks.domain.splash

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.tbcworks.data.dataStore.TokenDataStore
import com.example.tbcworks.databinding.FragmentSplashBinding
import com.example.tbcworks.presentation.BaseFragment
import kotlinx.coroutines.launch

class SplashFragment : BaseFragment<FragmentSplashBinding>() {
    private val tokenDataStore by lazy { TokenDataStore(requireContext()) }
    private val viewModel: SplashViewModel by viewModels {
        SplashViewModelFactory(tokenDataStore)
    }


    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSplashBinding {
        return FragmentSplashBinding.inflate(inflater, container, false)
    }

    override fun bind() {
        observe()
        viewModel.onEvent(SplashEvent.CheckToken)
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.sideEffect.collect { event ->
                    when (event) {
                        is SplashSideEffect.ToHome -> findNavController().navigate(
                            SplashFragmentDirections.actionSplashFragmentToHomeFragment(event.email)
                        )
                        SplashSideEffect.ToLogin -> findNavController().navigate(
                            SplashFragmentDirections.actionSplashFragmentToLoginFragment()
                        )
                    }
                }
            }
        }
    }



}