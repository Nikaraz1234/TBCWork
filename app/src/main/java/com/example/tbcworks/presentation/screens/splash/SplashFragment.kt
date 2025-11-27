package com.example.tbcworks.presentation.screens.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.tbcworks.databinding.FragmentSplashBinding
import com.example.tbcworks.presentation.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>() {
    private val viewModel: SplashViewModel by viewModels()


    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSplashBinding {
        return FragmentSplashBinding.inflate(inflater, container, false)
    }

    override fun bind() {
        observe()
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onEvent(SplashEvent.StartSplash)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.onEvent(SplashEvent.StopSplash)
    }




}