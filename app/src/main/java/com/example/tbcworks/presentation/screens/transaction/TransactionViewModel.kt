package com.example.tbcworks.presentation.screens.transaction

import androidx.lifecycle.viewModelScope
import com.example.tbcworks.domain.usecase.firebase.GetCurrentUserIdUseCase
import com.example.tbcworks.domain.usecase.transaction.SendTransactionUseCase
import com.example.tbcworks.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val sendTransactionUseCase: SendTransactionUseCase,
    private val getUserIdUseCase: GetCurrentUserIdUseCase
) : BaseViewModel<TransactionState, TransactionSideEffect, TransactionEvent>(
    initialState = TransactionState()
) {

    fun onEvent(event: TransactionEvent) {
        when (event) {
            is TransactionEvent.SendTransaction -> sendTransaction(event)
        }
    }

    private fun sendTransaction(event: TransactionEvent) {
        viewModelScope.launch {
            setState { copy(isLoading = true, message = null, error = null) }

            try {
                val senderId = getUserIdUseCase() ?: throw IllegalStateException("Current user ID is null")

                if (event is TransactionEvent.SendTransaction) {
                    sendTransactionUseCase(
                        senderId = senderId,
                        receiverEmail = event.receiverEmail,
                        amount = event.amount,
                        purpose = event.purpose,
                        imageUrl = event.imageUrl
                    )
                }

                setState { copy(isLoading = false, message = "Transaction completed", error = null) }
                sendSideEffect(TransactionSideEffect.ShowSnackBar("Transaction completed"))

            } catch (e: Exception) {
                setState { copy(isLoading = false, message = null, error = e.message ?: "Unknown error") }
                sendSideEffect(TransactionSideEffect.ShowSnackBar(e.message ?: "Unknown error"))
            }
        }
    }

}
