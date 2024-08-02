package com.masshow.data.repository

import com.masshow.data.model.BaseState
import com.masshow.data.model.request.LoginRequest
import com.masshow.data.model.response.LoginResponse
import com.masshow.data.model.runRemote
import com.masshow.data.remote.AuthApi
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi
): AuthRepository {

    override suspend fun login(body: LoginRequest): BaseState<LoginResponse?> = runRemote {
        api.login(body)
    }
}