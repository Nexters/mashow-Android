package com.masshow.data.repository

import com.masshow.data.model.BaseState
import com.masshow.data.model.request.RecordRequest

interface MainRepository {


    suspend fun record(
        body: RecordRequest
    ): BaseState<Unit?>
}