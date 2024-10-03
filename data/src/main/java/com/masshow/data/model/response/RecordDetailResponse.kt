package com.masshow.data.model.response

data class RecordDetailResponse(
    val drankAt: String,
    val liquorHistoryId: Int,
    val liquors: List<Liquor>,
    val memos: List<Memo>,
    val rating: Int,
    val sideDishes: List<SideDishe>
)

data class Memo(
    val description: String
)

data class Liquor(
    val details: List<Detail>,
    val liquorType: String
)

data class Detail(
    val names: String
)