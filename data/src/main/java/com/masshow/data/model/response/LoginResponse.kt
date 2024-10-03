package com.masshow.data.model.response

data class LoginResponse(
    val userId: Long,
    val nickname: String,
    val accessToken: String,
)
