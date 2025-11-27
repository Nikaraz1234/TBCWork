package com.example.tbcworks.presentation.screens.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tbcworks.databinding.FragmentHomeBinding
import com.example.tbcworks.presentation.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel: HomeViewModel by viewModels()
    private val args by navArgs<HomeFragmentArgs>()

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun bind() = with(binding){
        observeSideEffects()
        tvUsername.text = args.username
    }

    override fun listeners() = with(binding) {
        btnLogout.setOnClickListener {
            viewModel.onEvent(HomeEvent.LogoutClicked)
        }
        btnUserList.setOnClickListener {
            viewModel.onEvent(HomeEvent.UserListClicked)
        }
        btnUserInfo.setOnClickListener {
            viewModel.onEvent(HomeEvent.UserInfoClicked)
        }
    }

    private fun observeSideEffects() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.sideEffect.collect { sideEffect ->
                    when (sideEffect) {
                        HomeSideEffect.NavigateToLogin -> {
                            findNavController().navigate(
                                HomeFragmentDirections.actionHomeFragmentToLoginFragment()
                            )
                        }
                        HomeSideEffect.NavigateToDashboard -> {
                            findNavController().navigate(
                                HomeFragmentDirections.actionHomeFragmentToDashboardFragment()
                            )
                        }
                        HomeSideEffect.NavigateToUserInfo -> {
                            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToUserInfoFragment())

                        }
                    }
                }
            }
        }
    }
}
