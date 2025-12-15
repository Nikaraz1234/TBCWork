package com.example.tbcworks.presentation.screens.worker

sealed class WorkerSideEffect {
    data class ShowSnackBar(val message: String) : WorkerSideEffect()
}