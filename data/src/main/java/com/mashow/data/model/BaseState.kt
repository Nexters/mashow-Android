package com.mashow.data.model

enum class StatusCode {
    EMPTY,
    ERROR,
    EXCEPTION,
    ERROR_AUTH,
    ERROR_NONE
}

sealed class BaseState<out T> {
    data class Success<out T>(val data: T) : BaseState<T>()
    data class Error(val code: String, val message: String) :
        BaseState<Nothing>()
}