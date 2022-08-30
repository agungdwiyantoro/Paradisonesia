package com.devfutech.paradisonesia.presentation.fragments.signin

import androidx.lifecycle.viewModelScope
import com.devfutech.paradisonesia.data.local.preferences.AuthPreference
import com.devfutech.paradisonesia.domain.model.user.Customer
import com.devfutech.paradisonesia.domain.usecase.CustomerUseCase
import com.devfutech.paradisonesia.domain.usecase.RefreshTokenUseCase
import com.devfutech.paradisonesia.external.Resource
import com.devfutech.paradisonesia.presentation.base.BaseViewModel
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SigninViewModel @Inject constructor(
    private val customerUseCase: CustomerUseCase,
    private val authPreference: AuthPreference,
    private val refreshTokenUseCase: RefreshTokenUseCase
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
        //_googleSignIn.value = Resource.Loading()
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
                /*
                authPreference.apply {
                    setToken(it?.apiToken.toString())
                }

                 */
                Timber.tag("UserID").d(it?.id.toString())
                Timber.tag("UserName").d(it?.name.toString())
                Timber.tag("UserEmail").d(it?.email.toString())
                Timber.tag("UserPhone").d(it?.phone.toString())
                Timber.tag("UserEmaiilVerified").d(it?.is_email_verified.toString())
                Timber.tag("UserIsNewMember").d(it?.is_new_member.toString())
                Timber.tag("UserNote").d(it?.note.toString())

                Timber.tag("ProfileID").d(it?.profile?.id.toString())
                Timber.tag("ProfileUserID").d(it?.profile?.user_id.toString())
                Timber.tag("ProfileBirthDate").d(it?.profile?.birth_date.toString())
                Timber.tag("ProfileGender").d(it?.profile?.gender.toString())
                Timber.tag("ProfileAddress").d(it?.profile?.address.toString())
                Timber.tag("ProfileImage").d(it?.profile?.image)

                Timber.tag("StatusID").d(it?.status?.id.toString())
                Timber.tag("StatusName").d(it?.status?.name.toString())

                Timber.tag("CustomerLevelID").d(it?.customer_level?.id.toString())
                Timber.tag("CustomerLevelName").d(it?.customer_level?.name.toString())
                Timber.tag("CustomerLevelIcon").d(it?.customer_level?.icon.toString())

                Timber.tag("TokenToken_type").d(it?.token_type.toString())
                Timber.tag("TokenExpires_in").d(it?.expires_in.toString())
                Timber.tag("TokenAccess_token").d(it?.access_token.toString())
                Timber.tag("TokenRefresh_token").d(it?.refresh_token.toString())

                authPreference.apply {
                    setToken(it?.access_token.toString())
                    setRefreshToken(it?.refresh_token.toString())
                }

                Timber.tag("AuthPrefGetToken").d(authPreference.getToken())
                Timber.tag("AuthRefreshToken").d(authPreference.getRefreshToken())
            }
        }
    }
}