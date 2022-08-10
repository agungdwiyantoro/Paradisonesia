package com.devfutech.paradisonesia.presentation.fragments.account

import androidx.lifecycle.viewModelScope
import com.devfutech.paradisonesia.domain.model.merchant.MerchantStatus
import com.devfutech.paradisonesia.domain.usecase.MerchantUseCase
import com.devfutech.paradisonesia.external.Resource
import com.devfutech.paradisonesia.external.Event
import com.devfutech.paradisonesia.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val merchantUseCase: MerchantUseCase
) : BaseViewModel() {

    private val _status: MutableStateFlow<Event<Resource<MerchantStatus?>>> = MutableStateFlow(Event(Resource.Success()))
    val status:MutableStateFlow<Event<Resource<MerchantStatus?>>>
        get() = _status


    fun checkStatus(){
        _status.value = Event(Resource.Loading())
        viewModelScope.launch {
            merchantUseCase.merchantStatus()
                .catch { error->
                    onError(error)
                    _status.value = Event(Resource.Failure(defaultError(error)))
                }.collect {
                    if (it == null) _status.value = Event(Resource.Failure("Belum terdaftar"))
                    else _status.value = Event(Resource.Success(it))
                }
        }
    }
}