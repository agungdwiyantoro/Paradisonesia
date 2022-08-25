package com.devfutech.paradisonesia.presentation.fragments.edit_profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devfutech.paradisonesia.domain.model.banner.Banner
import com.devfutech.paradisonesia.domain.model.user.CustomerProfile
import com.devfutech.paradisonesia.domain.usecase.CustomerProfileUseCase
import com.devfutech.paradisonesia.domain.usecase.CustomerUseCase
import com.devfutech.paradisonesia.external.Resource
import com.devfutech.paradisonesia.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class EditProfileViewModel @Inject constructor(
    private val customerProfileUseCase: CustomerProfileUseCase

) : BaseViewModel(){
    private val _getCustomerProfile: MutableStateFlow<Resource<List<CustomerProfile>>> =
        MutableStateFlow(Resource.Success(emptyList()))

    val getCustomerProfile: MutableStateFlow<Resource<List<CustomerProfile>>>
        get() = _getCustomerProfile


    private fun checkUserProfileToServer(
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
            }.collect{
                customerProfileUseCase.apply {

                }
            }
        }

    }
}