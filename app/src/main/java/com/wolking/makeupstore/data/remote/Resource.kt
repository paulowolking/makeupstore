package com.wolking.makeupstore.data.remote

sealed class Resource<out T> {
    class Loading<out T> : Resource<T>()

    data class Success<out T>(val data: T) : Resource<T>()

    class Error<T>(message: String) : Resource<T>()
}