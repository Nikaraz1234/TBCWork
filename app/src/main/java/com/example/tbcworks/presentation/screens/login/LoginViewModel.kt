package com.example.tbcworks.presentation.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcworks.data.auth.repository.LoginRepository
import com.example.tbcworks.data.common.dataStore.TokenDataStore
import com.example.tbcworks.data.common.resource.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repo: LoginRepository,
    private val tokenDataStore: TokenDataStore
) : ViewModel() {


    private val _sideEffect = MutableSharedFlow<LoginSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()


    fun onEvent(event: LoginEvent){
        when (event) {
            is LoginEvent.OnLogin ->
                viewModelScope.launch {
                    login(event.email, event.password, event.rememberMe)
                }
            LoginEvent.OnRegister -> navigateToRegister()
        }
    }


    private suspend fun login(email: String, password: String, rememberMe: Boolean) {
        val error = validateInputs(email, password)
        if (error != null) {
            _sideEffect.emit(LoginSideEffect.ShowMessage(error))
            return
        }

        repo.login(email, password).collect { result ->
            when (result) {
                is Resource.Loading -> {}

                is Resource.Success -> {
                    if (rememberMe) {
                        result.data.token?.let { token ->
                            tokenDataStore.saveValue(TokenDataStore.TOKEN_KEY, token)
                            tokenDataStore.saveValue(TokenDataStore.EMAIL_KEY, email)
                        }
                    }
                    _sideEffect.emit(LoginSideEffect.ToHome)
                }

                is Resource.Error -> {
                    _sideEffect.emit(LoginSideEffect.ShowMessage(result.message))
                }
            }
        }
    }

    private fun navigateToRegister(){
        viewModelScope.launch {
            _sideEffect.emit(LoginSideEffect.ToRegister)
        }
    }

    private fun validateInputs(username: String, password: String): String? {
        if (username.isBlank() || password.isBlank()) return FIELD_EMPTY
        return null
    }
    companion object{
        private const val FIELD_EMPTY = "Username and password cannot be empty"
        private const val UNKNOWN_ERROR = "Unknown error"
        private const val NETWORK_ERROR = "NETWORK_ERROR"
    }

}
