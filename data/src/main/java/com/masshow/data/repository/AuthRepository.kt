package com.masshow.data.repository

import com.masshow.data.model.BaseState
import com.masshow.data.model.request.LoginRequest
import com.masshow.data.model.request.SignupRequest
import com.masshow.data.model.request.UserSimpleInfoQuery
import com.masshow.data.model.response.LoginResponse

interface AuthRepository {

    suspend fun login(
        body: LoginRequest
    ): BaseState<LoginResponse?>

    suspend fun signup(
        body: SignupRequest
    ): BaseState<LoginResponse?>

    suspend fun withdrawal(
        query: UserSimpleInfoQuery
    ): BaseState<Unit?>

    suspend fun getAccessToken(): String?
    suspend fun getRefreshToken(): String?
    suspend fun getUserId(): Long?
    suspend fun getNick(): String?
    suspend fun getLoginType(): String?

    suspend fun putAccessToken(token: String)
    suspend fun putRefreshToken(token: String)
    suspend fun putUserId(id: Long)
    suspend fun putNick(nick: String)
    suspend fun putLoginType(type: String)

    suspend fun deleteAccessToken()
    suspend fun deleteRefreshToken()
    suspend fun deleteUserId()
    suspend fun deleteNick()
    suspend fun deleteLoginType()

    suspend fun clear()
}