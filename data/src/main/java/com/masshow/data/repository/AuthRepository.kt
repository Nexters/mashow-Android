package com.masshow.data.repository

import com.masshow.data.model.BaseState
import com.masshow.data.model.request.LoginRequest
import com.masshow.data.model.request.SignupRequest
import com.masshow.data.model.response.LoginResponse

interface AuthRepository {

    suspend fun login(
        body: LoginRequest
    ): BaseState<LoginResponse?>

    suspend fun signup(
        body: SignupRequest
    ): BaseState<LoginResponse?>

    suspend fun getAccessToken(): String?
    suspend fun getRefreshToken(): String?

    suspend fun putAccessToken(token: String)
    suspend fun putRefreshToken(token: String)

    suspend fun deleteAccessToken()
    suspend fun deleteRefreshToken()
}