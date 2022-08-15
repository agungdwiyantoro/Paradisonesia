package com.devfutech.paradisonesia.presentation.base

import androidx.lifecycle.ViewModel
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import java.net.SocketTimeoutException

abstract class BaseViewModel : ViewModel() {

    fun onError(t: Throwable) {
        Timber.e("throwxx" + t)
    }

    fun defaultError(t: Throwable): String {
        return when (t) {
            is HttpException -> {
                return try {
                    val error = t.response()?.errorBody()?.string()
                    error.toString()
                }catch (e:Exception){
                    "Server Bermasalah"
                }
            }
            is SocketTimeoutException -> "Time Out"
            is IOException -> "Kesalahan Jaringan"
            else -> "Server Bermasalah"
        }
    }

}