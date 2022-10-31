package com.devfutech.paradisonesia.presentation.fragments.signin

import androidx.lifecycle.viewModelScope
import com.devfutech.paradisonesia.data.local.preferences.AuthPreference
import com.devfutech.paradisonesia.domain.model.user.Customer
import com.devfutech.paradisonesia.domain.usecase.CustomerUseCase
import com.devfutech.paradisonesia.domain.usecase.LogoutUseCase
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
class logoutViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
) : BaseViewModel() {

    private val _logout: MutableStateFlow<Resource<Boolean>> =
        MutableStateFlow(Resource.Success())
    val logout: MutableStateFlow<Resource<Boolean>>
        get() = _logout

    init {
        logout()
    }
    fun logout(){
        _logout.value = Resource.Loading()
        viewModelScope.launch {
            logoutUseCase.logout()
                .catch { error->
                    onError(error)
                    _logout.value = Resource.Failure(defaultError(error))
                    Timber.tag("KontolProduct").d("Gagal")

                }.collect {
                    _logout.value = Resource.Success()
                    Timber.tag("KontolProduct").d("Berhasil")
                }
        }
    }

}