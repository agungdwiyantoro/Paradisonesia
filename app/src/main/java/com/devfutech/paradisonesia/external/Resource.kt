package com.devfutech.paradisonesia.external

sealed class Resource<out T> {
    class Loading<out T> : Resource<T>()
    data class Success<out T>(val data: T? = null) : Resource<T>()
    data class Failure<out T>(val error: String?) : Resource<T>()
}