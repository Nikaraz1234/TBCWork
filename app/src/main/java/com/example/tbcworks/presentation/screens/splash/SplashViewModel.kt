package com.example.tbcworks.presentation.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcworks.data.common.dataStore.TokenDataStore

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.first
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(
    private val tokenDataStore: TokenDataStore
) : ViewModel() {

    private val _sideEffect = MutableSharedFlow<SplashSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    fun onEvent(event: SplashEvent){
        when (event) {
            is SplashEvent.StartSplash -> onStartSplash()
            is SplashEvent.StopSplash -> onStopSplash()
        }
    }
    private var splashJob: Job? = null

    private fun onStartSplash() {
        splashJob = viewModelScope.launch {
            delay(DELAY_DURATION)

            val token = tokenDataStore.getValue(TokenDataStore.TOKEN_KEY).first()
            val email = tokenDataStore.getValue(TokenDataStore.EMAIL_KEY).first() ?: ""
            if (!token.isNullOrEmpty()) {
                _sideEffect.emit(SplashSideEffect.ToHome(email))
            } else {
                _sideEffect.emit(SplashSideEffect.ToLogin)
            }
        }
    }

    private fun onStopSplash() {
        splashJob?.cancel()
    }

    companion object{
        private const val DELAY_DURATION = 2000L
    }
}