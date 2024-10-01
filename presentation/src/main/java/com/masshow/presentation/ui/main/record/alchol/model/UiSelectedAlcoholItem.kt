package com.masshow.presentation.ui.main.record.alchol.model

import com.masshow.presentation.util.Alcohol

data class UiSelectedAlcoholItem(
    val position: Int,
    val alcohol: Alcohol,
    val deleteSelect: (Int) -> Unit
)
