package com.nexters.data.model.response

data class BaseResponse<T>(
    val code: Int,
    val message: String,
    val value: T
)
