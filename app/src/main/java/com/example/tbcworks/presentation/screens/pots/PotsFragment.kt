package com.example.tbcworks.presentation.screens.pots

import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcworks.databinding.DialogAddPotBinding
import com.example.tbcworks.databinding.FragmentPotsBinding
import com.example.tbcworks.presentation.common.BaseFragment
import com.example.tbcworks.presentation.extension.SnackBarHelper.showSnackBar
import com.example.tbcworks.presentation.extension.collectFlow
import com.example.tbcworks.presentation.extension.collectStateFlow
import com.example.tbcworks.presentation.screens.pots.adapter.PotAdapter
import com.example.tbcworks.presentation.screens.pots.model.PotModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PotsFragment : BaseFragment<FragmentPotsBinding>(FragmentPotsBinding::inflate) {

    private val viewModel: PotsViewModel by viewModels()

    private val potAdapter by lazy {
        PotAdapter(
            onAddMoneyClick = { pot -> showAmountDialog(pot, isAdd = true) },
            onWithdrawClick = { pot -> showAmountDialog(pot, isAdd = false) },
            onEditClick = { pot -> showEditPotDialog(pot) },
            onDeleteClick = { pot -> confirmDeletePot(pot) }
        )
    }

    override fun listeners() = with(binding) {
        btnAdd.setOnClickListener { showAddPotDialog() }
    }

    override fun bind() {
        initRv()
        observeViewModel()
        viewModel.onEvent(PotEvent.LoadPots)
    }

    private fun initRv() {
        binding.rvPots.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPots.adapter = potAdapter
    }

    private fun observeViewModel() {
        collectFlow(viewModel.sideEffect) { effect ->
            when (effect) {
                is PotSideEffect.ShowSnackBar -> binding.root.showSnackBar(effect.message)
            }
        }

        collectStateFlow(viewModel.uiState) { state ->
            binding.rvPots.isVisible = state.pots.isNotEmpty()
            potAdapter.submitList(state.pots)
            state.error?.let { binding.root.showSnackBar(it) }
        }
    }

    private fun showAddPotDialog() {
        val dialogBinding = DialogAddPotBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .create()

        dialogBinding.btnAddPot.setOnClickListener {
            val title = dialogBinding.etPotTitle.text.toString().trim()
            val targetAmount = dialogBinding.etTargetAmount.text.toString().toDoubleOrNull() ?: 0.0

            if (title.isNotEmpty() && targetAmount > 0) {
                viewModel.onEvent(PotEvent.AddPot(title, targetAmount))
                dialog.dismiss()
            } else {
                binding.root.showSnackBar("Fill all fields with valid values")
            }
        }

        dialog.show()
    }

    private fun showEditPotDialog(pot: PotModel) {
        val dialogBinding = DialogAddPotBinding.inflate(layoutInflater)

        // Parse numeric value from formatted string
        val targetNumeric = pot.targetAmount

        dialogBinding.etPotTitle.setText(pot.title)
        dialogBinding.etTargetAmount.setText(targetNumeric.toString())

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .create()

        dialogBinding.btnAddPot.text = "Update"
        dialogBinding.btnAddPot.setOnClickListener {
            val newTitle = dialogBinding.etPotTitle.text.toString().trim()
            val newTarget = dialogBinding.etTargetAmount.text.toString().toDoubleOrNull() ?: 0.0

            if (newTitle.isNotEmpty() && newTarget > 0) {
                // Keep the same ID for editing
                val updatedPot = pot.copy(
                    title = newTitle,
                    targetAmount = newTarget
                )
                viewModel.onEvent(PotEvent.EditPot(updatedPot))
                dialog.dismiss()
            } else {
                binding.root.showSnackBar("Fill all fields with valid values")
            }
        }

        dialog.show()
    }

    private fun showAmountDialog(pot: PotModel, isAdd: Boolean) {
        val input = EditText(requireContext())
        input.inputType = android.text.InputType.TYPE_CLASS_NUMBER or android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL

        AlertDialog.Builder(requireContext())
            .setTitle(if (isAdd) "Add Money" else "Withdraw Money")
            .setView(input)
            .setPositiveButton("Confirm") { _, _ ->
                val amount = input.text.toString().toDoubleOrNull() ?: 0.0
                if (amount > 0) {
                    if (isAdd) viewModel.onEvent(PotEvent.AddMoney(pot, amount))
                    else viewModel.onEvent(PotEvent.WithdrawMoney(pot, amount))
                } else {
                    binding.root.showSnackBar("Enter a valid amount")
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun confirmDeletePot(pot: PotModel) {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete Pot")
            .setMessage("Are you sure you want to delete ${pot.title}?")
            .setPositiveButton("Yes") { _, _ ->
                viewModel.onEvent(PotEvent.DeletePot(pot))
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
