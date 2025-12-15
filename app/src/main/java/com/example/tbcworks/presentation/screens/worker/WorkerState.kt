package com.example.tbcworks.presentation.screens.worker

sealed class WorkerState {
    object Idle : WorkerState()
    object Running : WorkerState()
    object Success : WorkerState()
    object Failed : WorkerState()
}