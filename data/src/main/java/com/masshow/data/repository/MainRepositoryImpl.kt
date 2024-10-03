package com.masshow.data.repository

import com.masshow.data.model.BaseState
import com.masshow.data.model.request.MonthlyRecordRequest
import com.masshow.data.model.request.RecordRequest
import com.masshow.data.model.response.MonthlyRecordResponse
import com.masshow.data.model.response.RecordDetailResponse
import com.masshow.data.model.response.RecordExistLiquorResponse
import com.masshow.data.model.response.StatisticResponse
import com.masshow.data.model.runRemote
import com.masshow.data.remote.MainApi
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val api: MainApi
) : MainRepository {

    override suspend fun record(body: RecordRequest): BaseState<Unit?> = runRemote {
        api.record(body)
    }

    override suspend fun recordExistLiquor(): BaseState<RecordExistLiquorResponse?> = runRemote {
        api.getRecordExistLiquor()
    }

    override suspend fun getAlcoholStatistic(filters: List<String>): BaseState<StatisticResponse?> =
        runRemote {
            api.getAlcoholStatistic(filters)
        }

    override suspend fun getRecordDetail(historyId: Long): BaseState<RecordDetailResponse?> =
        runRemote {
            api.getRecordDetail(historyId)
        }

    override suspend fun getRecordMonthly(params: MonthlyRecordRequest): BaseState<MonthlyRecordResponse?> =
        runRemote {
            api.getRecordMonthly(params)
        }

    override suspend fun deleteRecord(historyId: Long): BaseState<Unit?> = runRemote {
        api.deleteRecord(historyId)
    }
}