package com.nexters.data.model

import com.google.gson.Gson
import retrofit2.Response

suspend fun <T> runRemote(block: suspend () -> Response<T>): BaseState<T> {
    return try {
        val response = block()
        if (response.isSuccessful) {
            response.body()?.let {
                BaseState.Success(it)
            } ?: run {
                BaseState.Error( "", "응답이 비어있습니다")
            }
        } else {
            val errorData = Gson().fromJson(response.errorBody()?.string(), BaseState.Error::class.java)
            BaseState.Error(errorData.code, errorData.message)
        }
    } catch (e: Exception) {
        BaseState.Error( "", e.message.toString())
    }
}