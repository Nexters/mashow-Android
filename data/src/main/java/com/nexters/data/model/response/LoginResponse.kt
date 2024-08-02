package com.nexters.data.model.response

data class LoginResponse(
    val userId: Long,
    val nickname: String,
    val oAuthProvider: String,
    val oAuthIdentity: String,
    val accessToken: String,
    val createdAt: String,
    val modifiedAt: String
)
