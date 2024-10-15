package com.mashow.data.remote

import com.mashow.data.model.request.LoginRequest
import com.mashow.data.model.request.SignupRequest
import com.mashow.data.model.request.UserSimpleInfoQuery
import com.mashow.data.model.response.BaseResponse
import com.mashow.data.model.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthApi {

    @POST("/user/login")
    suspend fun login(
        @Body body: LoginRequest
    ): Response<BaseResponse<LoginResponse>>

    @POST("/user/signup")
    suspend fun signup(
        @Body body: SignupRequest
    ): Response<BaseResponse<LoginResponse>>

    @DELETE("/user/account")
    suspend fun withdrawal(
        @Query("userSimpleInfo") userSimpleInfo : UserSimpleInfoQuery
    ): Response<BaseResponse<Unit>>
}