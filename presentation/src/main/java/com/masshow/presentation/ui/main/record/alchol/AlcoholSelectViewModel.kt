package com.masshow.presentation.ui.main.record.alchol

import androidx.lifecycle.ViewModel
import com.masshow.presentation.R
import com.masshow.presentation.ui.main.record.alchol.model.UiAlcoholSelectItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class AlcoholSelectUiState(
    val alcoholData: List<UiAlcoholSelectItem> = emptyList()
)

@HiltViewModel
class AlcoholSelectViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(AlcoholSelectUiState())
    val uiState: StateFlow<AlcoholSelectUiState> = _uiState.asStateFlow()

    init {
        setAlcoholData()
    }

    fun setAlcoholData() {
        _uiState.update { state ->
            state.copy(
                alcoholData = listOf(
                    UiAlcoholSelectItem(R.drawable.image_soju, R.drawable.text_soju),
                    UiAlcoholSelectItem(R.drawable.image_liquor, R.drawable.text_liquor),
                    UiAlcoholSelectItem(R.drawable.image_makguli, R.drawable.text_makguli),
                    UiAlcoholSelectItem(R.drawable.image_sake, R.drawable.text_sake),
                    UiAlcoholSelectItem(R.drawable.image_beer, R.drawable.text_beer),
                    UiAlcoholSelectItem(R.drawable.image_wine, R.drawable.text_wine),
                    UiAlcoholSelectItem(R.drawable.image_cocktail, R.drawable.text_cocktail),
                    UiAlcoholSelectItem(R.drawable.image_highball, R.drawable.text_highball)
                )
            )
        }
    }

}