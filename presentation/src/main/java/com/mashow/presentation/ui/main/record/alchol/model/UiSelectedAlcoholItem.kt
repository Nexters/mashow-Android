package com.mashow.presentation.ui.main.record.alchol.model

import com.mashow.presentation.util.Alcohol

data class UiSelectedAlcoholItem(
    val position: Int,
    val alcohol: Alcohol,
    val deleteSelect: (Int) -> Unit
)
