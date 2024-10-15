package com.mashow.data.remote

import com.mashow.data.model.request.MonthlyRecordRequest
import com.mashow.data.model.request.RecordRequest
import com.mashow.data.model.response.BaseResponse
import com.mashow.data.model.response.MonthlyRecordResponse
import com.mashow.data.model.response.RecordDetailResponse
import com.mashow.data.model.response.RecordExistLiquorResponse
import com.mashow.data.model.response.StatisticResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MainApi {


    @POST("/history/liquor")
    suspend fun record(
        @Body params: RecordRequest
    ): Response<BaseResponse<Unit>>

    @GET("/history/liquor-types")
    suspend fun getRecordExistLiquor(): Response<BaseResponse<RecordExistLiquorResponse>>

    @GET("/history/statistic")
    suspend fun getAlcoholStatistic(
        @Query("filters") filters: List<String>
    ): Response<BaseResponse<StatisticResponse>>

    @POST("/history/monthly")
    suspend fun getRecordMonthly(
        @Body params: MonthlyRecordRequest
    ): Response<BaseResponse<MonthlyRecordResponse>>

    @GET("/history/liquor/{historyId}")
    suspend fun getRecordDetail(
        @Path("historyId") historyId: Long
    ): Response<BaseResponse<RecordDetailResponse>>

    @DELETE("/history/liquor/{historyId}")
    suspend fun deleteRecord(
        @Path("historyId") historyId: Long
    ): Response<BaseResponse<Unit>>

}