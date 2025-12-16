package com.example.tbcworks.presentation.screens.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tbcworks.databinding.FragmentHomeBinding
import com.example.tbcworks.presentation.common.BaseFragment
import com.example.tbcworks.presentation.extension.SnackBarHelper.showSnackBar
import com.example.tbcworks.presentation.extension.collectFlow
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(
    FragmentHomeBinding::inflate
) {

    private val viewModel: HomeViewModel by viewModels()

    override fun listeners() = with(binding) {
        btnLogout.setOnClickListener {
            viewModel.onEvent(HomeEvent.Logout)
        }
        btnAddMoney.setOnClickListener {
            val amount = etAddMoney.text.toString().toDoubleOrNull() ?: 0.0
            viewModel.onEvent(HomeEvent.AddMoneyToUser(amount))
        }
    }

    override fun bind() {
        observeViewModel()
    }

    private fun observeViewModel() = with(binding) {
        collectFlow(viewModel.sideEffect) { effect ->
            when (effect) {
                is HomeSideEffect.NavigateToLogin -> {
                    findNavController().navigate(
                        HomeFragmentDirections.actionHomeFragmentToLoginFragment()
                    )
                }
                is HomeSideEffect.ShowSnackBar -> {
                    root.showSnackBar(effect.message)
                }
            }
        }

        collectFlow(viewModel.uiState) { state ->
            progressAddMoney.visibility = if (state.isLoading) View.VISIBLE else View.GONE
            btnAddMoney.isEnabled = !state.isLoading

            tvValue.text = "$${state.balance}"

            if (!state.isLoading) {
                etAddMoney.setText("")
            }
        }
    }
}
