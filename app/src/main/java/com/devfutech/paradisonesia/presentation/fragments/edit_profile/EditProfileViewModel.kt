package com.devfutech.paradisonesia.presentation.fragments.edit_profile

import androidx.lifecycle.viewModelScope
import com.devfutech.paradisonesia.data.local.preferences.AuthPreference
import com.devfutech.paradisonesia.domain.model.user.Customer
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
    private val customerUseCase: CustomerUseCase,
    private val authPreference: AuthPreference
) : BaseViewModel() {

    private val _customerProfile: MutableStateFlow<Resource<Customer>> =
        MutableStateFlow(Resource.Success())
    val customerProfile: MutableStateFlow<Resource<Customer>>
        get() = _customerProfile

    init {
        getCustomer()
    }

    fun getCustomer(){
        viewModelScope.launch {
            customerUseCase.customerProfile()
                .catch { error ->
                    onError(error)
                }.collect{
                    _customerProfile.value = Resource.Success(it)
                    Timber.tag("UserNameProfile").d(it.name.toString())
                    Timber.tag("UserEmailProfile").d(it.email.toString())
                    Timber.tag("UserPhoneProfile").d(it.phone.toString())
                    Timber.tag("UserEmaiilVerifiedProfile").d(it.is_email_verified.toString())
                }
        }
    }
    /*
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
                    setToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIyIiwianRpIjoiMTkyZWVmMmE2NDZkOTA5ZGZjZDU1YzNkM2Q4NWJhYWY4ZTM5MGNhYjEzN2U5MWVjYjgyYjdhYjYzYmQwOTZmMDM4MjM1NWZiNzBhYzlkZTMiLCJpYXQiOjE2NjE0ODUzMzIsIm5iZiI6MTY2MTQ4NTMzMiwiZXhwIjoxNjYxNDg4OTMwLCJzdWIiOiIyMCIsInNjb3BlcyI6W119.TFpCL8dq8_R22XrYXM_oadtgDKwu_QDa-o4XfdGNgKxMKL-r2oiZmNddVZWlVlLDa9eXTn6XZG9e7_IEBe1daxVAn6_tf5sxgF-6xxtHUgJMgNeJaUhRiYKHgx7wbWku9UVXcn5M5DnDW5XDnzhZeGjhY_M8Ff9q7AnLRyeevqD2QY81EHac8-RayIJrXggjBwHy7OMe0t_9E5fh5psjjrjsem3fbIUpqMWqo_jtHkrP8IB0nX3LEz8e2HYJxmJ79zPmlz4nRDe8tUM7L5TNCpmVmluZtHCuzkUGZQEMucW0lhIH840iyBYs0rBOIcnbE6wQQuo7kthGrjvSQX7LNwz2CB6EzdMRjROriRYlFXfwuFZGDpv5jre7pnujVW41eJRwt5MkILdjUa_enGQVqr6Tik7d1FH895AJqP1KBSf4vJzdDhn2V6S0908xzABB1_0FjUAGx2XFZQZEZumNgiNUbVtxyT4v2goSdzH28OPP-HxlzP-LNjloe2p328MNizXVnjTj_lIL4Bd2fNNhBGvpKPrY2U05aMFkU8FmkKSjCyOqO6AycoKCX-T0T2bkxFGnBhNU5yBhBbmdEoUJx4VftvXfd0Z7stbZGa47M1Q8OkjpCdc3KqrEpxtJwTUTkhecVa2MDAN75FpNASaabveG4UxeVfHQp_gkegHFalU")
                }.also {
                    Timber.tag("SHIT token").d(it?.getToken() )
               }

            }
        }
    }

     */
}