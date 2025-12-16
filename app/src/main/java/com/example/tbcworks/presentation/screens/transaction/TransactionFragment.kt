package com.example.tbcworks.presentation.screens.transaction

import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcworks.R
import com.example.tbcworks.databinding.DialogSendTransactionBinding
import com.example.tbcworks.databinding.FragmentTransactionBinding
import com.example.tbcworks.presentation.common.BaseFragment
import com.example.tbcworks.presentation.extension.SnackBarHelper.showSnackBar
import com.example.tbcworks.presentation.extension.collectFlow
import com.example.tbcworks.presentation.extension.collectStateFlow
import com.example.tbcworks.presentation.screens.transaction.adapter.TransactionAdapter
import com.example.tbcworks.presentation.screens.transaction.model.TransactionModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TransactionFragment : BaseFragment<FragmentTransactionBinding>(
    FragmentTransactionBinding::inflate
) {

    private val viewModel: TransactionViewModel by viewModels()
    private val transactionAdapter by lazy { TransactionAdapter() }

    override fun listeners() {
        with(binding){
            btnSendTransaction.setOnClickListener {
                showSendTransactionDialog()
            }
            etSearch.addTextChangedListener { text ->
                val query = text.toString()
                viewModel.onEvent(TransactionEvent.SearchTransactions(query))
            }
            btnSort.setOnClickListener {
                showSortDialog()
            }
        }
    }

    override fun bind() {
        setUpRv()
        observers()
        viewModel.onEvent(TransactionEvent.LoadTransactions)
    }

    private fun setUpRv(){
        binding.rvTransactions.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = transactionAdapter

            val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            ContextCompat.getDrawable(context, R.drawable.rv_divider)?.let { divider.setDrawable(it) }
            addItemDecoration(divider)
        }
    }

    private fun observers() {
        collectStateFlow(viewModel.uiState) { state ->
            transactionAdapter.submitList(state.filteredTransactions)
            binding.progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        }

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
                dialogBinding.root.showSnackBar(getString(R.string.enter_valid_email_amount))
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

    private fun showSortDialog() {
        val fields = arrayOf(getString(R.string.amount), getString(R.string.date))
        val orders = arrayOf(getString(R.string.ascending), getString(R.string.descending))

        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.sort_by))
            .setItems(fields) { _, fieldIndex ->
                val field = when (fieldIndex) {
                    0 -> TransactionModel.SortField.DATE
                    1 -> TransactionModel.SortField.AMOUNT
                    else -> TransactionModel.SortField.DATE
                }

                AlertDialog.Builder(requireContext())
                    .setTitle(getString(R.string.sort_order))
                    .setItems(orders) { _, orderIndex ->
                        val ascending = orderIndex == 0
                        viewModel.onEvent(TransactionEvent.SortTransactions(field, ascending))
                    }
                    .show()
            }
            .show()
    }
}


