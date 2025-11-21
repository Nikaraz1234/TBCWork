package com.example.tbcworks.presentation.extensions

import android.view.View
import com.google.android.material.snackbar.Snackbar

object SnackBarHelper {

    fun View.showSnackBar(message: String, duration: Int = Snackbar.LENGTH_SHORT) {
        Snackbar.make(this, message, duration).show()
    }

}