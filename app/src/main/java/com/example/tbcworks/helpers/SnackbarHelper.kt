package com.example.tbcworks.helpers

import android.view.View
import com.google.android.material.snackbar.Snackbar

object SnackbarHelper {
        fun show(view: View, message: String, duration: Int = Snackbar.LENGTH_SHORT) {
            Snackbar.make(view, message,duration).show()
        }
}