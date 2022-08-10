package com.devfutech.paradisonesia.external.extension

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException


fun Task<AuthResult>.mapFirebaseAuthException(): String? {
    println("Result : ${exception?.message}")
    return when (exception) {
        is FirebaseAuthUserCollisionException -> {
            //If email alread
            exception?.message
        }
        is FirebaseAuthInvalidCredentialsException -> {
            //If email are in incorret  format
            exception?.message
        }
        is FirebaseAuthWeakPasswordException -> {
            //if password not 'stronger'
            exception?.message
        }
        else -> {
            exception?.message
        }
    }
}