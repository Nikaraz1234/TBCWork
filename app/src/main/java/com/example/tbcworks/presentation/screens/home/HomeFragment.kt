package com.example.tbcworks.presentation.screens.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.tbcworks.databinding.FragmentHomeBinding
import com.example.tbcworks.presentation.common.BaseFragment
import com.example.tbcworks.presentation.extension.SnackBarHelper.showSnackBar
import com.example.tbcworks.presentation.extension.collectFlow
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(
    FragmentHomeBinding::inflate
) {

    private val viewModel: HomeViewModel by viewModels()

    override fun listeners() = with(binding){
        btnLogout.setOnClickListener {
            viewModel.onEvent(HomeEvent.Logout)
        }
        binding.btnAddMoney.setOnClickListener {
            val amountText = binding.etAddMoney.text.toString()
            val amount = amountText.toDoubleOrNull() ?: 0.0
            viewModel.onEvent(HomeEvent.AddMoneyToUser(amount))
        }
    }

    override fun bind() {
        observers()
    }
    private fun observers(){
        collectFlow(viewModel.sideEffect) { sideEffect ->
            when (sideEffect) {
                is HomeSideEffect.NavigateToLogin -> {
                    findNavController().navigate(
                        HomeFragmentDirections.actionHomeFragmentToLoginFragment()
                    )
                }
                is HomeSideEffect.ShowSnackBar -> {
                    binding.root.showSnackBar(sideEffect.message)
                }
            }
        }
        collectFlow(viewModel.balance) { currentBalance ->
            binding.etAddMoney.setText("")
            binding.tvValue.text = "$currentBalance"
        }

    }

    override fun onResume() {
        super.onResume()
        viewModel.onEvent(HomeEvent.LoadBalance)
    }
}
