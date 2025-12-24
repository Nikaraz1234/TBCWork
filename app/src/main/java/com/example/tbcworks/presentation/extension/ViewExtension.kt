package com.example.tbcworks.presentation.extension

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import coil.load
import com.example.tbcworks.R
import com.google.android.material.snackbar.Snackbar

fun ImageView.loadImage(
    url: String?,
    placeholderRes: Int = R.drawable.ic_launcher_background,
    errorRes: Int = R.drawable.ic_launcher_background,
    enableCrossfade: Boolean = true,
) {
    this.load(url) {
        placeholder(placeholderRes)
        error(errorRes)
        crossfade(enableCrossfade)
    }
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun View.showSnackBar(message: String, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, message, duration).show()
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}