package com.example.tbcworks.presentation.screens.lock_screen

import com.example.tbcworks.presentation.screens.lock_screen.models.DotModel
import com.example.tbcworks.presentation.screens.lock_screen.models.NumberModel

data class LockScreenState(
    val dots: List<DotModel> = emptyList(),
    val keys: List<NumberModel> = defaultKeys()
)
fun defaultKeys(): List<NumberModel> {
    val numbers = (1..9).map { NumberModel(NumberModel.BtnType.NUMBER, it.toString()) }
    return numbers + listOf(
        NumberModel(NumberModel.BtnType.FINGERPRINT),
        NumberModel(NumberModel.BtnType.NUMBER, "0"),
        NumberModel(NumberModel.BtnType.BACKSPACE)
    )
}

