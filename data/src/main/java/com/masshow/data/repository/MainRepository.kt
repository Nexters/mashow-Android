package com.masshow.data.repository

import com.masshow.data.model.BaseState
import com.masshow.data.model.request.MonthlyRecordRequest
import com.masshow.data.model.request.RecordRequest
import com.masshow.data.model.response.BaseResponse
import com.masshow.data.model.response.MonthlyRecordResponse
import com.masshow.data.model.response.RecordDetailResponse
import com.masshow.data.model.response.RecordExistLiquorResponse
import com.masshow.data.model.response.StatisticResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MainRepository {


    suspend fun record(
        body: RecordRequest
    ): BaseState<Unit?>

    suspend fun recordExistLiquor(): BaseState<RecordExistLiquorResponse?>

    suspend fun getAlcoholStatistic(
        filters: List<String>
    ): BaseState<StatisticResponse?>

    suspend fun getRecordMonthly(
        params: MonthlyRecordRequest
    ): BaseState<MonthlyRecordResponse?>

    suspend fun getRecordDetail(
        historyId: Long
    ): BaseState<RecordDetailResponse?>

    suspend fun deleteRecord(
        historyId: Long
    ): BaseState<Unit?>
}