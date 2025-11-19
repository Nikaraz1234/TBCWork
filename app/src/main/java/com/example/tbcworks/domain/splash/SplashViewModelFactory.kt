package com.example.tbcworks.domain.splash

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tbcworks.data.dataStore.TokenDataStore

class SplashViewModelFactory(private val tokenDataStore: TokenDataStore) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            return SplashViewModel(tokenDataStore) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}