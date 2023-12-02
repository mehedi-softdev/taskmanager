package com.innovatenestlabs.taskmanager.utils

sealed class Response<T>(val data: T? = null, val message: String? = null) {
    class Loading<T>() : Response<T>()
    class Success<T>(data: T, message: String? = null) :
        Response<T>(data = data, message = message)

    class Error<T>(message: String) : Response<T>(message = message)
}