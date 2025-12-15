package com.example.tbcworks.presentation.screens.transaction

sealed class TransactionSideEffect {
    data class ShowSnackBar(val message: String) : TransactionSideEffect()
}
