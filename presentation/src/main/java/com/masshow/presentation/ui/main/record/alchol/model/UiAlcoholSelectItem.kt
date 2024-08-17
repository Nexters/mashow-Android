package com.masshow.presentation.ui.main.record.alchol.model

data class UiAlcoholSelectItem(
    val name: String,
    val alcoholImage: Int,
    val alcoholText: Int,
    val isSelected: Boolean,
    val adding : (Int) -> Unit
)
