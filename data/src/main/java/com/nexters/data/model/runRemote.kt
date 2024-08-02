package com.nexters.data.model

import com.google.gson.Gson
import com.nexters.data.model.response.BaseResponse
import retrofit2.Response

suspend fun <T> runRemote(block: suspend () -> Response<BaseResponse<T>>): BaseState<T> {
    return try {
        val response = block()
        if (response.isSuccessful) {
            response.body()?.let {
                BaseState.Success(it.value)
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