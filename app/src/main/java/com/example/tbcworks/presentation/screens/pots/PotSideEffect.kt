package com.example.tbcworks.presentation.screens.pots


sealed class PotSideEffect {
    data class ShowSnackBar(val message: String) : PotSideEffect()
}