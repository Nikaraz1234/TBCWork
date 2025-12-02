package com.example.tbcworks.presentation.extension


import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.showSnackbar(message: String, length: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, message, length).show()
}

fun View.showSnackbar(messageResId: Int, length: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, messageResId, length).show()
}