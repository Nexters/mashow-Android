package com.nexters.data.repository

import com.nexters.data.model.BaseState
import com.nexters.data.model.request.LoginRequest
import com.nexters.data.model.response.LoginResponse

interface AuthRepository {

    suspend fun login(
        body: LoginRequest
    ): BaseState<LoginResponse>
}