package com.devfutech.paradisonesia.presentation.fragments.edit_profile

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devfutech.paradisonesia.data.local.preferences.AuthPreference
import com.devfutech.paradisonesia.domain.model.banner.Banner
import com.devfutech.paradisonesia.domain.model.user.Customer
import com.devfutech.paradisonesia.domain.model.user.CustomerProfile
import com.devfutech.paradisonesia.domain.usecase.CustomerProfileUseCase
import com.devfutech.paradisonesia.domain.usecase.CustomerUseCase
import com.devfutech.paradisonesia.external.Resource
import com.devfutech.paradisonesia.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val customerProfileUseCase: CustomerProfileUseCase,
    private val authPreference: AuthPreference
) : BaseViewModel() {

    private val _googleSignIn: MutableStateFlow<Resource<CustomerProfile>> =
        MutableStateFlow(Resource.Success())
    val googleSignIn: MutableStateFlow<Resource<CustomerProfile>>
        get() = _googleSignIn


    fun checkUserToServer(
        name: String,
        email: String,
        phone: String,
        address: String,
        gender: String,
        birth_date: String,
        image: String
    ) {
        viewModelScope.launch {
            customerProfileUseCase.profileCustomer(
                mapOf(
                    "name" to name,
                    "email" to email,
                    "phone" to phone,
                    "address" to address,
                    "gender" to gender,
                    "birth_date" to birth_date,
                    "image" to image
                )
            ).catch { error ->
                onError(error)
            }.collect {
                _googleSignIn.value = Resource.Success(it)
               authPreference.apply {
                    setToken(it?.apiToken.toString())
                }.also {
                    Timber.tag("SHIT token").d(authPreference.getToken() )
               }

            }
        }
    }
}