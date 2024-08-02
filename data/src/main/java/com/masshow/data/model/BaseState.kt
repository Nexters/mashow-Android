package com.masshow.data.model

enum class StatusCode {
    EMPTY,
    ERROR,
    EXCEPTION,
    ERROR_AUTH,
    ERROR_NONE
}

sealed class BaseState<out T> {
    data class Success<out T>(val code: Int, val data: T) : BaseState<T>()
    data class Error(val code: Int, val message: String) :
        BaseState<Nothing>()
}