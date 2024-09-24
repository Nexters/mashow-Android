package com.masshow.presentation.ui.main.record.alchol

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masshow.presentation.ui.main.record.alchol.model.UiAlcoholSelectItem
import com.masshow.presentation.ui.main.record.alchol.model.UiSelectedAlcoholItem
import com.masshow.presentation.util.Alcohol
import com.masshow.presentation.util.Constants
import com.masshow.presentation.util.Constants.TAG
import com.masshow.presentation.util.getTodayDateWithDay
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

sealed class AlcoholSelectEvent {
    data object ChangeSelectedAlcohol : AlcoholSelectEvent()
    data class NavigateToAlcoholSelectDetail(val list: List<String>) :
        AlcoholSelectEvent()
}

@HiltViewModel
class AlcoholSelectViewModel @Inject constructor() : ViewModel() {

    private val _event = MutableSharedFlow<AlcoholSelectEvent>()
    val event: SharedFlow<AlcoholSelectEvent> = _event.asSharedFlow()

    private val _uiState = MutableStateFlow(AlcoholSelectUiState())
    val uiState: StateFlow<AlcoholSelectUiState> = _uiState.asStateFlow()

    val date = getTodayDateWithDay()

    val alcoholData = MutableStateFlow<List<UiAlcoholSelectItem>>(emptyList())

    init {
        setAlcoholData()
    }

    private fun setAlcoholData() {
        alcoholData.update {
            listOf(
                UiAlcoholSelectItem(
                    Alcohol.SOJU, false,
                    ::addAlcohol
                ),
                UiAlcoholSelectItem(
                    Alcohol.LIQUOR, false,
                    ::addAlcohol
                ),
                UiAlcoholSelectItem(
                    Alcohol.MAKGEOLLI, false, ::addAlcohol
                ),
                UiAlcoholSelectItem(
                    Alcohol.SAKE, false,
                    ::addAlcohol
                ),
                UiAlcoholSelectItem(
                    Alcohol.BEER, false,
                    ::addAlcohol
                ),
                UiAlcoholSelectItem(
                    Alcohol.WINE, false,
                    ::addAlcohol
                ),
                UiAlcoholSelectItem(
                    Alcohol.COCKTAIL, false, ::addAlcohol
                ),
                UiAlcoholSelectItem(
                    Alcohol.HIGHBALL, false, ::addAlcohol
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
        Log.d(TAG, position.toString())
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

    fun navigateToAlcoholDetail() {
        viewModelScope.launch {
            _event.emit(AlcoholSelectEvent.NavigateToAlcoholSelectDetail(uiState.value.selectedItemList.map{
                Constants.alcoholMap[it.position] ?: run{ "" }
            }))
        }
    }

}