package com.example.tbcworks.presentation.extension

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.StateFlow

fun <T> LifecycleOwner.collectFlow(
    flow: Flow<T>,
    state: (T) -> Unit
) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collect { state(it) }
        }
    }
}

fun <T> LifecycleOwner.collectStateFlow(
    flow: StateFlow<T>,
    state: (T) -> Unit
) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collect { state(it) }
        }
    }
}