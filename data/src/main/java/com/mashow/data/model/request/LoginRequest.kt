package com.mashow.data.model.request

data class LoginRequest(
    val provider: String,
    val oAuthToken: String
)
