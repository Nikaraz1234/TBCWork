package com.example.tbcworks.presentation.screens.splash

import androidx.lifecycle.viewModelScope
import com.example.tbcworks.domain.usecase.datastore.GetTokenUseCase
import com.example.tbcworks.domain.usecase.firebase.GetCurrentUserIdUseCase
import com.example.tbcworks.presentation.common.BaseViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getTokenUseCase: GetTokenUseCase,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase
) : BaseViewModel<SplashState, SplashSideEffect, SplashEvent>(
    initialState = SplashState()
) {

    private var splashJob: Job? = null

    fun onEvent(event: SplashEvent) {
        when (event) {
            is SplashEvent.StartSplash -> onStartSplash()
            is SplashEvent.StopSplash -> onStopSplash()
        }
    }

    private fun onStartSplash() {
        splashJob = viewModelScope.launch {
            delay(DELAY_DURATION)

            val token = getTokenUseCase().first()
            val currentUser = getCurrentUserIdUseCase()

            if (currentUser != null && !token.isNullOrEmpty()) {
                sendSideEffect(SplashSideEffect.ToHome)
            } else {
                sendSideEffect(SplashSideEffect.ToLogin)
            }
        }
    }

    private fun onStopSplash() {
        splashJob?.cancel()
    }

    companion object {
        private const val DELAY_DURATION = 2000L
    }
}
