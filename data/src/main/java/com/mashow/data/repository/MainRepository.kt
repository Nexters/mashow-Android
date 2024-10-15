package com.mashow.data.repository

import com.mashow.data.model.BaseState
import com.mashow.data.model.request.MonthlyRecordRequest
import com.mashow.data.model.request.RecordRequest
import com.mashow.data.model.response.MonthlyRecordResponse
import com.mashow.data.model.response.RecordDetailResponse
import com.mashow.data.model.response.RecordExistLiquorResponse
import com.mashow.data.model.response.StatisticResponse

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