package com.masshow.data.repository

import com.masshow.data.model.BaseState
import com.masshow.data.model.request.LoginRequest
import com.masshow.data.model.response.LoginResponse

interface AuthRepository {

    suspend fun login(
        body: LoginRequest
    ): BaseState<LoginResponse?>
}