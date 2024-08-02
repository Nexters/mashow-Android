package com.mashow.data.repository

import com.mashow.data.model.BaseState
import com.mashow.data.model.request.LoginRequest
import com.mashow.data.model.response.LoginResponse

interface AuthRepository {

    suspend fun login(
        body: LoginRequest
    ): BaseState<LoginResponse>
}