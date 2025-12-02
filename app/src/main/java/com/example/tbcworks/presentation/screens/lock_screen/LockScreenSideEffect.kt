package com.example.tbcworks.presentation.screens.lock_screen

sealed interface LockScreenSideEffect {
    object WrongPasscode : LockScreenSideEffect
    object CorrectPasscode : LockScreenSideEffect
    object FingerprintClicked : LockScreenSideEffect
}