package com.example.tbcworks.presentation.screens.worker

sealed class WorkerEvent {
    object StartCleanup : WorkerEvent()
}
