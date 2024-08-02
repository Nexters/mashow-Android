package com.nexters.data.repository

import com.nexters.data.model.BaseState
import com.nexters.data.model.request.LoginRequest
import com.nexters.data.model.response.LoginResponse
import com.nexters.data.model.runRemote
import com.nexters.data.remote.AuthApi
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi
): AuthRepository {

    override suspend fun login(body: LoginRequest): BaseState<LoginResponse> = runRemote {
        api.login(body)
    }
}