package com.example.tbcworks.presentation.screens.lock_screen

import com.example.tbcworks.presentation.screens.lock_screen.models.NumberModel

sealed interface LockScreenEvent {
    data class BtnPressed(val btn: NumberModel) : LockScreenEvent
}