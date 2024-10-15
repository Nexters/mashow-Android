package com.mashow.data.model.request

data class SignupRequest(
    val provider: String,
    val nickname: String,
    val oAuthToken: String
)
