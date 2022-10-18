package com.devfutech.paradisonesia.presentation.fragments.merchant_register

import androidx.lifecycle.viewModelScope
import com.devfutech.paradisonesia.domain.usecase.MerchantUseCase
import com.devfutech.paradisonesia.external.Resource
import com.devfutech.paradisonesia.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MerchantRegisterViewModel @Inject constructor(
    private val merchantUseCase: MerchantUseCase
) : BaseViewModel() {

    private val _submit: MutableStateFlow<Resource<String>> = MutableStateFlow(Resource.Success())
    val submit: MutableStateFlow<Resource<String>>
        get() = _submit


    fun merchantRegister(
        payload: Map<String, String>,
        payloadLong: Map<String, Long>
        //ktp: MultipartBody.Part?,
        //npwp: MultipartBody.Part?,
        //siup: MultipartBody.Part?
    ) {
        _submit.value = Resource.Loading()
        viewModelScope.launch {
            merchantUseCase.merchantRegister(payload = payload, payloadLong = payloadLong/*, ktp = ktp, npwp = npwp, siup = siup*/)
                .catch { error ->
                    onError(error)
                    _submit.value = Resource.Failure(defaultError(error))
                    Timber.tag("ERROR").d(defaultError(error))
                    Timber.tag("MerchantRegisterViewModel").d("Merchant Registration Failed")
                }.collect {
                    _submit.value = Resource.Success(it)
                    Timber.tag("MerchantRegisterViewModel").d("Merchant Registration Success")
                }
        }
    }
}