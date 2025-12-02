package com.example.tbcworks.presentation.screens.lock_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcworks.presentation.screens.lock_screen.models.DotModel
import com.example.tbcworks.presentation.screens.lock_screen.models.NumberModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LockScreenViewModel @Inject constructor() : ViewModel(){

    private val _state = MutableStateFlow(LockScreenState(
        dots = List(PASSCODE.length) { DotModel(filled = false) }
    ))
    val state = _state.asStateFlow()
    private val _sideEffect = MutableSharedFlow<LockScreenSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()
    private var passcode = ""


    fun onEvent(event: LockScreenEvent) {
        when (event) {
            is LockScreenEvent.BtnPressed -> handleClick(event.btn)
        }
    }

    private fun handleClick(btn: NumberModel){
        when (btn.type) {
            NumberModel.BtnType.NUMBER -> {
                if (passcode.length < PASSCODE_LENGTH && btn.value != null) {
                    passcode += btn.value
                }
            }
            NumberModel.BtnType.BACKSPACE -> {
                if (passcode.isNotEmpty()) {
                    passcode = passcode.dropLast(1)
                }
            }
            NumberModel.BtnType.FINGERPRINT -> {
                viewModelScope.launch {
                    _sideEffect.emit(LockScreenSideEffect.FingerprintClicked)
                }
            }
        }
        updateDots()
        if (passcode.length == PASSCODE_LENGTH) {
            checkPasscode()
        }
    }

    private fun updateDots() {
        val dots = List(PASSCODE_LENGTH) { index ->
            DotModel(filled = index < passcode.length)
        }

        _state.update { it.copy(dots = dots) }
    }
    private fun checkPasscode() {
        if (passcode == PASSCODE) {
            passcode = ""
            updateDots()
            viewModelScope.launch {
                _sideEffect.emit(LockScreenSideEffect.CorrectPasscode)
            }
        } else {
            passcode = ""
            updateDots()
            viewModelScope.launch {
                _sideEffect.emit(LockScreenSideEffect.WrongPasscode)
            }
        }
    }

    companion object {
        private const val PASSCODE = "0934"
        private const val PASSCODE_LENGTH = 4
    }
}