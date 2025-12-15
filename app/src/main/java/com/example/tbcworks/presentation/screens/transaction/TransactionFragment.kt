package com.example.tbcworks.presentation.screens.transaction

import androidx.appcompat.app.AlertDialog
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.example.tbcworks.R
import com.example.tbcworks.databinding.DialogAddPotBinding
import com.example.tbcworks.databinding.DialogSendTransactionBinding
import com.example.tbcworks.databinding.FragmentTransactionBinding
import com.example.tbcworks.presentation.common.BaseFragment
import com.example.tbcworks.presentation.extension.SnackBarHelper.showSnackBar
import com.example.tbcworks.presentation.extension.collectFlow
import com.example.tbcworks.presentation.extension.collectStateFlow
import com.example.tbcworks.presentation.screens.pots.model.PotModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import java.util.UUID


@AndroidEntryPoint
class TransactionFragment : BaseFragment<FragmentTransactionBinding>(
    FragmentTransactionBinding::inflate
) {

    private val viewModel: TransactionViewModel by viewModels()

    override fun listeners() {
        binding.btnSendTransaction.setOnClickListener {
            showSendTransactionDialog()
        }
    }

    override fun bind() {
        observers()
    }
    private fun observers() {

        // Observe UI state (loading)
        collectStateFlow(viewModel.uiState) { state ->
            //binding.progressBar.isVisible = state.isLoading
        }

        // Observe one-time side effects (Snackbar, etc.)
        collectFlow(viewModel.sideEffect) { effect ->
            when (effect) {
                is TransactionSideEffect.ShowSnackBar -> binding.root.showSnackBar(effect.message)
            }
        }
    }

    private fun showSendTransactionDialog() {
        val dialogBinding = DialogSendTransactionBinding.inflate(layoutInflater)

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .create()

        dialogBinding.btnSend.setOnClickListener {
            val receiverEmail = dialogBinding.etReceiverEmail.text.toString().trim()
            val amount = dialogBinding.etAmount.text.toString().toDoubleOrNull() ?: 0.0
            val purpose = dialogBinding.etPurpose.text.toString().trim()

            if (receiverEmail.isEmpty() || amount <= 0.0) {
                dialogBinding.root.showSnackBar("Enter valid email and amount")
                return@setOnClickListener
            }

            viewModel.onEvent(
                TransactionEvent.SendTransaction(
                    receiverEmail = receiverEmail,
                    amount = amount,
                    purpose = purpose,
                    imageUrl = null,
                )
            )

            dialog.dismiss()
        }

        dialog.show()
    }


}

