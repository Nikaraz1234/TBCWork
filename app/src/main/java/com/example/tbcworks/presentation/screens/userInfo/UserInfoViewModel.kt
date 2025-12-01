package com.example.tbcworks.presentation.screens.userInfo


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcworks.domain.usecase.proto.ReadUserUseCase
import com.example.tbcworks.domain.usecase.proto.SaveUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserInfoViewModel @Inject constructor(
    private val saveUserUseCase: SaveUserUseCase,
    private val readUserUseCase: ReadUserUseCase
) : ViewModel() {
    private val _sideEffect = MutableSharedFlow<UserInfoSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    fun onEvent(event: UserInfoEvent) {
        when (event) {
            is UserInfoEvent.SaveUser -> saveUser(event.firstName, event.lastName, event.email)
            is UserInfoEvent.ReadUser -> readUser()
        }
    }

    private fun saveUser(first: String, last: String, email: String) {
        viewModelScope.launch {
            saveUserUseCase(first, last, email)
        }
    }

    private fun readUser() {
        viewModelScope.launch {
            val user = readUserUseCase().first()
            _sideEffect.emit(UserInfoSideEffect.ShowUser(
                user.firstName,
                user.lastName,
                user.email
            ))
        }
    }

    fun validateInput(first: String, last: String, email: String): Boolean {
        return if (first.isBlank()) {
            false
        } else if (last.isBlank()) {
            false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            false
        }else {
            true
        }
    }

}
