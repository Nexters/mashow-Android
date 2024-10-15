package com.mashow.data.model.response

data class StatisticResponse(
    val names: List<StatisticItem>,
    val frequencyPercentage: Int
)

data class StatisticItem(
    val name: String,
    val count: Int
)
