package com.devfutech.paradisonesia.external.extension

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

fun Context.toast(message: String?) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun View.snackBar(message: String?) = Snackbar.make(this, "$message", Snackbar.LENGTH_SHORT).show()

fun View.visible(){
    visibility = View.VISIBLE
}

fun View.gone(){
    visibility = View.GONE
}

fun TextInputLayout.inputError(data: String, message: String?): Boolean {
    return if (data.isEmpty()) {
        error = message
        false
    } else {
        error = null
        true
    }
}

fun TextInputEditText.clearInput(
    inputLayout: TextInputLayout
) {
    setOnFocusChangeListener { _, hasFoccus ->
        if (hasFoccus) {
            inputLayout.error = null
        }
    }
}