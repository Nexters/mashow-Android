package com.mashow.data.repository

import com.mashow.data.model.BaseState
import com.mashow.data.model.request.LoginRequest
import com.mashow.data.model.response.LoginResponse
import com.mashow.data.model.runRemote
import com.mashow.data.remote.AuthApi
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi
): AuthRepository {

    override suspend fun login(body: LoginRequest): BaseState<LoginResponse> = runRemote {
        api.login(body)
    }
}