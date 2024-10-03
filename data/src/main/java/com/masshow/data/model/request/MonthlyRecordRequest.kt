package com.masshow.data.model.request

data class MonthlyRecordRequest(
    val filters: List<String>,
    val userId: Long,
    val paginationRequest: PaginationData
)

data class PaginationData(
    val page: Int,
    val size: Int
)
