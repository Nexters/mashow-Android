package com.masshow.data.remote

import com.masshow.data.model.request.RecordRequest
import com.masshow.data.model.response.BaseResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface MainApi {


    @POST("/history/liquor")
    suspend fun record(
        @Body params : RecordRequest
    ): Response<BaseResponse<Unit>>

}