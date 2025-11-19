package com.example.tbcworks.domain.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcworks.data.dataStore.TokenDataStore
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.first
class SplashViewModel(
    private val tokenDataStore: TokenDataStore
) : ViewModel() {

    private val _sideEffect = MutableSharedFlow<SplashSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    fun onEvent(event: SplashEvent){
        when (event) {
            is SplashEvent.CheckToken -> checkToken()
        }
    }

    private fun checkToken() {
        viewModelScope.launch {
            delay(DELAY_DURATION)

            val token = tokenDataStore.getToken().first()
            val email = tokenDataStore.getEmail().first() ?: ""
            if (!token.isNullOrEmpty()) {
                _sideEffect.emit(SplashSideEffect.ToHome(email))
            } else {
                _sideEffect.emit(SplashSideEffect.ToLogin)
            }
        }
    }
    companion object{
        private const val DELAY_DURATION = 2000L
    }
}