package com.example.tbcworks.presentation.screens.transaction

import androidx.lifecycle.viewModelScope
import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.usecase.firebase.GetCurrentUserIdUseCase
import com.example.tbcworks.domain.usecase.transaction.GetTransactionsUseCase
import com.example.tbcworks.domain.usecase.transaction.SendTransactionUseCase
import com.example.tbcworks.presentation.common.BaseViewModel
import com.example.tbcworks.presentation.screens.transaction.mapper.toPresentation
import com.example.tbcworks.presentation.screens.transaction.model.TransactionModel
import com.example.tbcworks.presentation.screens.transaction.strings.TransactionStrings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val sendTransactionUseCase: SendTransactionUseCase,
    private val getTransactionsUseCase: GetTransactionsUseCase,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
) : BaseViewModel<TransactionState, TransactionSideEffect, TransactionEvent>(
    initialState = TransactionState()
) {

    fun onEvent(event: TransactionEvent) {
        when (event) {
            is TransactionEvent.SendTransaction -> sendTransaction(event)
            is TransactionEvent.LoadTransactions -> loadTransactions()
            is TransactionEvent.SearchTransactions -> filterTransactions(event.query)
            is TransactionEvent.SortTransactions -> {
                sortTransactions(event.field, event.ascending)
            }
        }
    }

    private fun filterTransactions(query: String) {
        setState {
            val filtered = if (query.isBlank()) {
                transactions
            } else {
                transactions.filter { t ->
                    t.purpose.contains(query, ignoreCase = true) ||
                            t.receiverEmail.contains(query, ignoreCase = true) ||
                            t.value.toString().contains(query)
                }
            }
            copy(filteredTransactions = applySort(filtered, sortField, sortAscending))
        }
    }

    private fun sendTransaction(event: TransactionEvent.SendTransaction) {
        viewModelScope.launch {
            val senderId = getCurrentUserIdUseCase() ?: run {
                setState { copy(error = TransactionStrings.CURRENT_USER_NULL) }
                sendSideEffect(TransactionSideEffect.ShowSnackBar(TransactionStrings.CURRENT_USER_NULL))
                return@launch
            }

            sendTransactionUseCase(
                senderId = senderId,
                receiverEmail = event.receiverEmail,
                amount = event.amount,
                purpose = event.purpose,
                imageUrl = event.imageUrl
            ).collect { resource ->
                when (resource) {
                    is Resource.Loading -> setState { copy(isLoading = true, message = null, error = null) }
                    is Resource.Success -> {
                        setState { copy(isLoading = false, message = TransactionStrings.TRANSACTION_COMPLETED, error = null) }
                        sendSideEffect(TransactionSideEffect.ShowSnackBar(TransactionStrings.TRANSACTION_COMPLETED))
                        onEvent(TransactionEvent.LoadTransactions)
                    }
                    is Resource.Error -> {
                        setState { copy(isLoading = false, message = null, error = resource.message ?: TransactionStrings.UNKNOWN_ERROR) }
                        sendSideEffect(TransactionSideEffect.ShowSnackBar(resource.message ?: TransactionStrings.UNKNOWN_ERROR))
                    }
                }
            }
        }
    }

    private fun loadTransactions() {
        viewModelScope.launch {
            val userId = getCurrentUserIdUseCase() ?: run {
                setState { copy(error = TransactionStrings.CURRENT_USER_NULL) }
                sendSideEffect(TransactionSideEffect.ShowSnackBar(TransactionStrings.CURRENT_USER_NULL))
                return@launch
            }

            getTransactionsUseCase(userId).collect { resource ->
                when (resource) {
                    is Resource.Loading -> setState { copy(isLoading = true, message = null, error = null) }
                    is Resource.Success -> {
                        val listModel = resource.data.map { it.toPresentation() }
                        setState {
                            copy(
                                transactions = listModel,
                                filteredTransactions = applySort(listModel, sortField, sortAscending),
                                isLoading = false,
                                error = null
                            )
                        }
                    }
                    is Resource.Error -> {
                        setState { copy(isLoading = false, error = resource.message) }
                        sendSideEffect(TransactionSideEffect.ShowSnackBar(resource.message))
                    }
                }
            }
        }
    }

    private fun sortTransactions(field: TransactionModel.SortField, ascending: Boolean) {
        setState {
            copy(
                filteredTransactions = applySort(filteredTransactions, field, ascending),
                sortField = field,
                sortAscending = ascending
            )
        }
    }

    private fun applySort(
        list: List<TransactionModel>,
        field: TransactionModel.SortField,
        ascending: Boolean
    ): List<TransactionModel> {

        val sorted = when (field) {
            TransactionModel.SortField.DATE ->
                list.sortedBy { it.date }

            TransactionModel.SortField.AMOUNT ->
                list.sortedBy { it.value }
        }

        return if (ascending) sorted else sorted.reversed()
    }
}
