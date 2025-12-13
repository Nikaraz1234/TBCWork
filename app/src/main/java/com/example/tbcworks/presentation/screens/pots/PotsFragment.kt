package com.example.tbcworks.presentation.screens.pots

import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcworks.R
import com.example.tbcworks.databinding.DialogAddPotBinding
import com.example.tbcworks.databinding.FragmentPotsBinding
import com.example.tbcworks.presentation.common.BaseFragment
import com.example.tbcworks.presentation.extension.SnackBarHelper.showSnackBar
import com.example.tbcworks.presentation.extension.collectFlow
import com.example.tbcworks.presentation.extension.collectStateFlow
import com.example.tbcworks.presentation.screens.pots.adapter.PotAdapter
import com.example.tbcworks.presentation.screens.pots.model.PotModel
import com.example.tbcworks.presentation.screens.signup.SignUpFragmentDirections
import com.example.tbcworks.presentation.screens.signup.SignUpSideEffect
import com.example.tbcworks.presentation.screens.splash.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.UUID
import kotlin.getValue

@AndroidEntryPoint
class PotsFragment : BaseFragment<FragmentPotsBinding>(
    FragmentPotsBinding::inflate
) {
    private val viewModel: PotsViewModel by viewModels()

    private val potAdapter by lazy {
        PotAdapter(
            onAddMoneyClick = { pot ->
                binding.root.showSnackBar("Add money to ${pot.title}")
            },
            onWithdrawClick = { pot ->
                binding.root.showSnackBar("Withdraw from ${pot.title}")
            },
            onOptionsClick = { pot ->
                binding.root.showSnackBar("Options for ${pot.title}")
            }
        )
    }

    override fun listeners() {
        binding.btnAdd.setOnClickListener {
            showAddPotDialog()
        }
    }

    override fun bind() {
        observers()
        initRv()

        viewModel.onEvent(PotEvent.LoadPots)
    }
    private fun initRv() {
        binding.rvPots.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPots.adapter = potAdapter
    }
    private fun showAddPotDialog() {
        val dialogBinding = DialogAddPotBinding.inflate(layoutInflater)

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .create()

        dialogBinding.btnAddPot.setOnClickListener {
            val title = dialogBinding.etPotTitle.text.toString()
            val targetAmount = dialogBinding.etTargetAmount.text.toString().toDoubleOrNull() ?: 0.0

            if (title.isNotEmpty() && targetAmount > 0) {
                viewModel.onEvent(PotEvent.AddPot(title, targetAmount))
                dialog.dismiss()
            } else {
                binding.root.showSnackBar("Fill all fields")
            }
        }

        dialog.show()
    }
     fun observers() {

        collectFlow(viewModel.sideEffect) { effect ->
            when (effect) {
                is PotSideEffect.ShowSnackBar -> binding.root.showSnackBar("Pot added")
            }
        }

         collectStateFlow(viewModel.uiState) { state ->
             binding.rvPots.isVisible = state.pots.isNotEmpty()
             potAdapter.submitList(state.pots)

             //binding.progressBar.isVisible = state.isLoading
             state.error?.let { binding.root.showSnackBar(it) }
         }

     }


}