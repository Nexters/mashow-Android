package com.masshow.presentation.ui.main.record.alchol.model

import com.masshow.presentation.util.Alcohol

data class UiAlcoholSelectItem(
    val alcohol: Alcohol,
    val isSelected: Boolean,
    val hideAddBtn: Boolean,
    val adding : (Int) -> Unit,
    val delete : (Int) -> Unit
)
