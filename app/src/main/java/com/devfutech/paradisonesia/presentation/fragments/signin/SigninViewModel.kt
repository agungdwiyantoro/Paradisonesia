package com.devfutech.paradisonesia.presentation.fragments.signin

import androidx.lifecycle.viewModelScope
import com.devfutech.paradisonesia.data.local.preferences.AuthPreference
import com.devfutech.paradisonesia.domain.model.user.Customer
import com.devfutech.paradisonesia.domain.usecase.CustomerUseCase
import com.devfutech.paradisonesia.external.Resource
import com.devfutech.paradisonesia.presentation.base.BaseViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SigninViewModel @Inject constructor(
    private val customerUseCase: CustomerUseCase,
    private val authPreference: AuthPreference
) : BaseViewModel() {

    private val _googleSignIn: MutableStateFlow<Resource<Customer>> =
        MutableStateFlow(Resource.Success())
    val googleSignIn: MutableStateFlow<Resource<Customer>>
        get() = _googleSignIn

    fun firebaseAuthWithProvider(idToken: String, isGoogle: Boolean) {
        //_googleSignIn.value = Resource.Loading()
        val credential = if (isGoogle) GoogleAuthProvider.getCredential(
            idToken,
            null
        )  else FacebookAuthProvider.getCredential(idToken)
        Firebase.auth
            .signInWithCredential(credential)
            .addOnSuccessListener { task ->
                FirebaseMessaging.getInstance().token.addOnCompleteListener { fcm ->
                    if (!fcm.isSuccessful) {
                        return@addOnCompleteListener
                    }
                    checkUserToServer(
                        name = task.user?.displayName.toString(),
                        email = task.user?.email.toString(),
                        uid = task.user?.uid.toString(),
                        fcmToken = fcm.result,
                        isEmailVerified = if (task.user?.isEmailVerified == true) "1" else "0"
                    )
                }
            }.addOnFailureListener { task ->
                _googleSignIn.value = Resource.Failure(task.message)
            }
    }

    fun signInAuth(email: String, password: String) {
        _googleSignIn.value = Resource.Loading()
        Firebase.auth
            .signInWithEmailAndPassword(email, password)
            .addOnSuccessListener { task ->
                FirebaseMessaging.getInstance().token.addOnCompleteListener { fcm ->
                    if (!fcm.isSuccessful) {
                        return@addOnCompleteListener
                    }
                    checkUserToServer(
                        name = task.user?.displayName.toString(),
                        email = task.user?.email.toString(),
                        uid = task.user?.uid.toString(),
                        fcmToken = fcm.result,
                        isEmailVerified = if (task.user?.isEmailVerified == true) "1" else "0"
                    )
                }
            }.addOnFailureListener { task ->
                _googleSignIn.value = Resource.Failure(task.message)
            }
    }

    private fun checkUserToServer(
        name: String,
        email: String,
        uid: String,
        fcmToken: String,
        isEmailVerified: String
    ) {
        viewModelScope.launch {
            customerUseCase.authCustomer(
                mapOf(
                    "name" to name,
                    "email" to email,
                    "user_uid" to uid,
                    "fcm_token" to fcmToken,
                    "is_email_verivied" to isEmailVerified,
                )
            ).catch { error ->
                onError(error)
            }.collect {
                _googleSignIn.value = Resource.Success(it)
                authPreference.apply {
                    setToken(it?.apiToken.toString())
                }
            }
        }
    }
}