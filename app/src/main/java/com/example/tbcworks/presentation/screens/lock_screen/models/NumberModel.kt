package com.example.tbcworks.presentation.screens.lock_screen.models

data class NumberModel(
    val type: BtnType,
    val value: String? = null
){
    enum class BtnType {
        NUMBER,
        BACKSPACE,
        FINGERPRINT
    }
}

