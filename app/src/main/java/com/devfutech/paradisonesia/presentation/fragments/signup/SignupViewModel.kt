package com.devfutech.paradisonesia.presentation.fragments.signup

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.devfutech.paradisonesia.external.Resource
import com.devfutech.paradisonesia.external.extension.mapFirebaseAuthException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(

): ViewModel() {

    private val _signUp: MutableStateFlow<Resource<FirebaseUser?>> =
        MutableStateFlow(Resource.Success())
    val signUp: MutableStateFlow<Resource<FirebaseUser?>>
        get() = _signUp

    private val _googleSignIn: MutableStateFlow<Resource<AuthResult>> =
        MutableStateFlow(Resource.Success())
    val googleSignIn: MutableStateFlow<Resource<AuthResult>>
        get() = _googleSignIn

    fun firebaseAuthWithProvider(activity: Activity, idToken: String, isGoogle: Boolean) {
        val credential = if (isGoogle) GoogleAuthProvider.getCredential(
            idToken,
            null
        ) else FacebookAuthProvider.getCredential(idToken)
        Firebase.auth
            .signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    _googleSignIn.value = Resource.Success(task.result)
                } else {
                    _googleSignIn.value = Resource.Failure(task.mapFirebaseAuthException())
                }
            }
    }

    fun createFirebaseUser(email: String, password: String) {
        Firebase.auth
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { result ->
                if (result.isSuccessful) {
                    _signUp.value = Resource.Success(Firebase.auth.currentUser)
                } else {
                    _signUp.value = Resource.Failure(result.mapFirebaseAuthException())
                }
            }
    }
}