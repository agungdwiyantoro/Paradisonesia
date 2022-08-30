package com.devfutech.paradisonesia.external

import okhttp3.ResponseBody

sealed class Resource<out T> {
    class Loading<out T> : Resource<T>()
    data class Success<out T>(val data: T? = null) : Resource<T>()
    data class Failure<out T>(val error: String?) : Resource<T>()

    data class FailureX(
        val isNetworkError: Boolean,
        val errorCode: Int?,
        val errorBody: ResponseBody?
    ) : Resource<Nothing>()
}