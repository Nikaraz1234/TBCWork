package com.example.tbcworks.presentation.screens

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tbcworks.data.auth.ApiClient
import com.example.tbcworks.data.auth.repository.LoginRepository
import com.example.tbcworks.data.auth.repository.RegisterRepository
import com.example.tbcworks.data.dataStore.TokenDataStore
import com.example.tbcworks.presentation.screens.login.LoginViewModel
import com.example.tbcworks.presentation.screens.register.RegisterViewModel

class AuthViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        val loginRepository = LoginRepository(ApiClient.loginApi)
        val registerRepository = RegisterRepository(ApiClient.registerApi)

        val tokenDataStore = TokenDataStore(context.applicationContext)

        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) ->
                LoginViewModel(loginRepository, tokenDataStore) as T

            modelClass.isAssignableFrom(RegisterViewModel::class.java) ->
                RegisterViewModel(registerRepository, tokenDataStore) as T

            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}


