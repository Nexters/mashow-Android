package com.masshow.presentation.ui.main.show.model

data class UiRecordItem(
    val date: String,
    val count: String,
    val items: List<UiRecordChip>
)

data class UiRecordChip(
    val id: Long,
    val date: String,
    val color: Int,
    val navigateToDetail: (Long) -> Unit
)
