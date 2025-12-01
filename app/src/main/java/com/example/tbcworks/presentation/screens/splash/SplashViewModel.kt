package com.example.tbcworks.presentation.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcworks.data.common.dataStore.TokenDataStore
import com.example.tbcworks.domain.usecase.datastore_pref.GetUserSessionUseCase

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
    private val getUserSessionUseCase: GetUserSessionUseCase
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

            val session = getUserSessionUseCase().first()
            if (!session.token.isNullOrEmpty()) {
                _sideEffect.emit(SplashSideEffect.ToHome(session.email ?: ""))
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