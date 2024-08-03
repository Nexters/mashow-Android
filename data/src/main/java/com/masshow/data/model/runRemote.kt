package com.masshow.data.model

import com.google.gson.Gson
import com.masshow.data.model.response.BaseResponse
import retrofit2.Response

suspend fun <T> runRemote(block: suspend () -> Response<BaseResponse<T>>): BaseState<T?> {
    return try {
        val response = block()
        if (response.isSuccessful) {
            response.body()?.let {
                BaseState.Success(it.code,it.value)
            } ?: run {
                BaseState.Error( 0, "응답이 비어있습니다")
            }
        } else {
            val errorData = Gson().fromJson(response.errorBody()?.string(), BaseState.Error::class.java)
            BaseState.Error(errorData.code, errorData.message)
        }
    } catch (e: Exception) {
        BaseState.Error( 0, e.message.toString())
    }
}