package com.masshow.data.repository

import com.masshow.data.model.BaseState
import com.masshow.data.model.request.RecordRequest
import com.masshow.data.model.response.RecordExistLiquorResponse
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
}