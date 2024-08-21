package com.masshow.presentation.ui.main.record.alchol

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masshow.presentation.R
import com.masshow.presentation.ui.main.record.alchol.model.UiAlcoholSelectItem
import com.masshow.presentation.ui.main.record.alchol.model.UiSelectedAlcoholItem
import com.masshow.presentation.util.Constants.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AlcoholSelectUiState(
    val currentItem: Int = 0,
    val selectedItemList: List<UiSelectedAlcoholItem> = emptyList()
)

sealed class AlcoholSelectEvent{
    data object ChangeSelectedAlcohol: AlcoholSelectEvent()
}

@HiltViewModel
class AlcoholSelectViewModel @Inject constructor() : ViewModel() {

    private val _event = MutableSharedFlow<AlcoholSelectEvent>()
    val event : SharedFlow<AlcoholSelectEvent> = _event.asSharedFlow()

    private val _uiState = MutableStateFlow(AlcoholSelectUiState())
    val uiState: StateFlow<AlcoholSelectUiState> = _uiState.asStateFlow()

    val alcoholData = MutableStateFlow<List<UiAlcoholSelectItem>>(emptyList())

    init {
        setAlcoholData()
    }

    private fun setAlcoholData() {
        alcoholData.update {
            listOf(
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
        }
    }

    private fun addAlcohol(position: Int) {
        if (uiState.value.selectedItemList.size != 3) {
            val item = UiSelectedAlcoholItem(position, ::deleteSelected)
            if (!uiState.value.selectedItemList.contains(item)) {
                _uiState.update { state ->
                    state.copy(
                        selectedItemList = uiState.value.selectedItemList + item
                    )
                }
            }

            alcoholData.update {
                alcoholData.value.mapIndexed { index, item ->
                    if (index == position) {
                        item.copy(
                            isSelected = true
                        )
                    } else {
                        item.copy()
                    }
                }
            }

            viewModelScope.launch {
                _event.emit(AlcoholSelectEvent.ChangeSelectedAlcohol)
            }
        }

    }

    private fun deleteSelected(position: Int) {
        Log.d(TAG,position.toString())
        _uiState.update { state ->
            state.copy(
                selectedItemList = uiState.value.selectedItemList.filter {
                    it.position != position
                }
            )
        }

        alcoholData.update {
            alcoholData.value.mapIndexed { index, item ->
                if (index == position) {
                    item.copy(
                        isSelected = false
                    )
                } else {
                    item.copy()
                }
            }
        }

        viewModelScope.launch {
            _event.emit(AlcoholSelectEvent.ChangeSelectedAlcohol)
        }
    }

    fun setCurrentData(position: Int) {
        _uiState.update { state ->
            state.copy(
                currentItem = position
            )
        }
    }

}