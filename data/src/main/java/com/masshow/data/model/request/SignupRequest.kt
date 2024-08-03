package com.masshow.data.model.request

data class SignupRequest(
    val provider: String,
    val nickname: String,
    val oAuthToken: String
)
