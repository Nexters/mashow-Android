package com.masshow.presentation.ui.main.record.alchol

import androidx.lifecycle.ViewModel
import com.masshow.presentation.R
import com.masshow.presentation.ui.main.record.alchol.model.UiAlcoholSelectItem
import com.masshow.presentation.ui.main.record.alchol.model.UiSelectedAlcoholItem
import com.masshow.presentation.util.Constants.alcoholMap
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class AlcoholSelectUiState(
    val alcoholData: List<UiAlcoholSelectItem> = emptyList(),
    val currentItem: Int = 0,
    val selectedItemList: List<UiSelectedAlcoholItem> = emptyList()
)

@HiltViewModel
class AlcoholSelectViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(AlcoholSelectUiState())
    val uiState: StateFlow<AlcoholSelectUiState> = _uiState.asStateFlow()

    init {
        setAlcoholData()
    }

    private fun setAlcoholData() {
        _uiState.update { state ->
            state.copy(
                alcoholData = listOf(
                    UiAlcoholSelectItem(
                        "소주",
                        R.drawable.image_soju,
                        R.drawable.text_soju, false,
                        ::addAlcohol
                    ),
                    UiAlcoholSelectItem(
                        "양주",
                        R.drawable.image_liquor,
                        R.drawable.text_liquor, false,
                        ::addAlcohol
                    ),
                    UiAlcoholSelectItem(
                        "막걸리",
                        R.drawable.image_makguli,
                        R.drawable.text_makguli, false, ::addAlcohol
                    ),
                    UiAlcoholSelectItem(
                        "사케",
                        R.drawable.image_sake,
                        R.drawable.text_sake, false,
                        ::addAlcohol
                    ),
                    UiAlcoholSelectItem(
                        "맥주",
                        R.drawable.image_beer,
                        R.drawable.text_beer, false,
                        ::addAlcohol
                    ),
                    UiAlcoholSelectItem(
                        "와인",
                        R.drawable.image_wine,
                        R.drawable.text_wine, false,
                        ::addAlcohol
                    ),
                    UiAlcoholSelectItem(
                        "칵테일",
                        R.drawable.image_cocktail,
                        R.drawable.text_cocktail, false, ::addAlcohol
                    ),
                    UiAlcoholSelectItem(
                        "하이볼",
                        R.drawable.image_highball,
                        R.drawable.text_highball, false, ::addAlcohol
                    )
                )
            )
        }
    }

    private fun addAlcohol(position: Int) {
        val item = UiSelectedAlcoholItem(position, ::deleteSelected)
        if (!uiState.value.selectedItemList.contains(item)) {
            _uiState.update { state ->
                state.copy(
                    selectedItemList = uiState.value.selectedItemList + item
                )
            }
        }
    }

    private fun deleteSelected(position: Int) {

    }

    fun setCurrentData(position: Int) {
        _uiState.update { state ->
            state.copy(
                currentItem = position
            )
        }
    }

}