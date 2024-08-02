package com.nexters.data.model.request

data class LoginRequest(
    val provider: String,
    val oAuthToken: String
)
