package com.mashow.data.model.request

data class RecordRequest(
    val liquors: List<LiquorItem>,
    val memos: List<MemoItem>,
    val rating: Int,
    val sideDishes: List<SideDishItem>
)

data class LiquorItem(
    val liquorType: String,
    val names: List<String>
)

data class MemoItem(
    val description: String
)

data class SideDishItem(
    val title: String
)