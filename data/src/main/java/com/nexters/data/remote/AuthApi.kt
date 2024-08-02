package com.nexters.data.remote

import com.nexters.data.model.request.LoginRequest
import com.nexters.data.model.response.BaseResponse
import com.nexters.data.model.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("/user/login")
    suspend fun login(
        @Body body: LoginRequest
    ): Response<BaseResponse<LoginResponse>>
}