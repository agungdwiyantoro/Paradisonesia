package com.devfutech.paradisonesia.presentation.fragments.refresh_token

import android.content.Context
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.devfutech.paradisonesia.R
import com.devfutech.paradisonesia.data.local.preferences.AuthPreference
import com.devfutech.paradisonesia.domain.model.PriceID
import com.devfutech.paradisonesia.domain.model.city.City
import com.devfutech.paradisonesia.domain.model.product.Product
import com.devfutech.paradisonesia.domain.model.token.Token
import com.devfutech.paradisonesia.domain.usecase.ProductUseCase
import com.devfutech.paradisonesia.domain.usecase.RefreshTokenUseCase
import com.devfutech.paradisonesia.external.Resource
import com.devfutech.paradisonesia.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


class RefreshTokenViewModel @Inject constructor(
    private val tokenUseCase: RefreshTokenUseCase,
    private val authPreference: AuthPreference
) : BaseViewModel() {

    init {
        getTokenRefresh(authPreference.getRefreshToken())
    }

    private val _refreshToken: MutableStateFlow<Resource<Token>> =
        MutableStateFlow(Resource.Success())
    val refreshToken : MutableStateFlow<Resource<Token>>
        get() = _refreshToken

    fun getTokenRefresh(tokenRefresh: String) {
        _refreshToken.value = Resource.Loading()
        viewModelScope.launch {
            tokenUseCase.tokenAuthRefresh(tokenRefresh)
                .catch { error ->
                    onError(error)
                    _refreshToken.value = Resource.Failure(defaultError(error))
                    Timber.tag("RepresToken").d("RepresError")

                }.collect {
                    _refreshToken.value = Resource.Success(it)
                    authPreference.setToken(it?.access_token!!)
                    Timber.tag("RepresToken").d("RepresToken Success" + it)
                }
        }
    }
}
