package com.devfutech.paradisonesia.presentation.fragments.edit_profile

import androidx.lifecycle.ViewModel
import com.devfutech.paradisonesia.domain.model.banner.Banner
import com.devfutech.paradisonesia.domain.usecase.CustomerUseCase
import com.devfutech.paradisonesia.external.Resource
import com.devfutech.paradisonesia.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class EditProfileViewModel @Inject constructor(
    private val customerUseCase: CustomerUseCase

) : BaseViewModel(){
    private val _getAuthToken: MutableStateFlow<Resource<List<Banner>>> =
        MutableStateFlow(Resource.Success(emptyList()))
    val getAuthToken: MutableStateFlow<Resource<List<Banner>>>
        get() = _getAuthToken
}