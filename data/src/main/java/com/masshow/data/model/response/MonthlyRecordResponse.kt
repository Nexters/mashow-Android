package com.masshow.data.model.response

data class MonthlyRecordResponse(
    val contents: List<Content>,
    val currentPageIndex: Int,
    val isLastPage: Boolean,
    val pageSize: Int,
    val totalElementNumber: Int,
    val totalPageNumber: Int
)

data class History(
    val drankAt: String,
    val historyId: Int,
    val liquorDetailNames: List<String>
)

data class Content(
    val histories: List<History>,
    val month: Int,
    val year: Int
)