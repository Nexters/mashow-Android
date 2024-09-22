package com.masshow.data.repository

import com.masshow.data.model.BaseState
import com.masshow.data.model.request.RecordRequest
import com.masshow.data.model.response.RecordExistLiquorResponse

interface MainRepository {


    suspend fun record(
        body: RecordRequest
    ): BaseState<Unit?>

    suspend fun recordExistLiquor(): BaseState<RecordExistLiquorResponse?>
}