package com.masshow.presentation.ui.main.record.alchol

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masshow.presentation.ui.main.record.RecordFormData
import com.masshow.presentation.ui.main.record.alchol.model.UiAlcoholSelectItem
import com.masshow.presentation.ui.main.record.alchol.model.UiSelectedAlcoholItem
import com.masshow.presentation.util.Alcohol
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
    data object NavigateToAlcoholSelectDetail : AlcoholSelectEvent()
    data object NavigateToBack : AlcoholSelectEvent()
    data object FinishRecord : AlcoholSelectEvent()
}

@HiltViewModel
class AlcoholSelectViewModel @Inject constructor() : ViewModel() {

    private val _event = MutableSharedFlow<AlcoholSelectEvent>()
    val event: SharedFlow<AlcoholSelectEvent> = _event.asSharedFlow()

    private val _uiState = MutableStateFlow(AlcoholSelectUiState())
    val uiState: StateFlow<AlcoholSelectUiState> = _uiState.asStateFlow()

    val date = getTodayDateWithDay()

    val alcoholData = MutableStateFlow<List<UiAlcoholSelectItem>>(emptyList())

    companion object {
        val alcoholMap = hashMapOf(
            0 to "소주",
            1 to "양주",
            2 to "막걸리",
            3 to "사케",
            4 to "맥주",
            5 to "와인",
            6 to "칵테일",
            7 to "하이볼"
        )
    }

    init {
        setAlcoholData()
    }

    private fun setAlcoholData() {
        alcoholData.update {
            listOf(
                UiAlcoholSelectItem(
                    Alcohol.SOJU, false, false,
                    ::addAlcohol,
                    ::deleteSelected
                ),
                UiAlcoholSelectItem(
                    Alcohol.LIQUOR, false, false,
                    ::addAlcohol,
                    ::deleteSelected
                ),
                UiAlcoholSelectItem(
                    Alcohol.MAKGEOLLI, false, false, ::addAlcohol,
                    ::deleteSelected
                ),
                UiAlcoholSelectItem(
                    Alcohol.SAKE, false, false,
                    ::addAlcohol,
                    ::deleteSelected
                ),
                UiAlcoholSelectItem(
                    Alcohol.BEER, false, false,
                    ::addAlcohol,
                    ::deleteSelected
                ),
                UiAlcoholSelectItem(
                    Alcohol.WINE, false, false,
                    ::addAlcohol,
                    ::deleteSelected
                ),
                UiAlcoholSelectItem(
                    Alcohol.COCKTAIL, false, false, ::addAlcohol,
                    ::deleteSelected
                ),
                UiAlcoholSelectItem(
                    Alcohol.HIGHBALL, false, false, ::addAlcohol,
                    ::deleteSelected
                )
            )
        }
    }

    private fun addAlcohol(position: Int) {

        if (uiState.value.selectedItemList.size != 1) {
            val item = UiSelectedAlcoholItem(
                position,
                Alcohol.displayNameToEnum(alcoholMap[position]!!),
                ::deleteSelected
            )
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

            var state = true
            RecordFormData.selectedAlcoholList.forEach {
                if (it.first == item.alcohol) state = false
            }
            if (state) {
                RecordFormData.selectedAlcoholList.add(
                    Pair(
                        item.alcohol,
                        mutableListOf()
                    )
                )
            }

            viewModelScope.launch {
                _event.emit(AlcoholSelectEvent.ChangeSelectedAlcohol)
            }
        }

        if(uiState.value.selectedItemList.size == 1){
            alcoholData.update {
                it.map { data ->
                    if(!data.isSelected){
                        data.copy(
                            hideAddBtn = true
                        )
                    } else {
                        data.copy()
                    }
                }
            }
        }

    }

    private fun deleteSelected(position: Int) {

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

        RecordFormData.selectedAlcoholList = RecordFormData.selectedAlcoholList.filter {
            it.first != Alcohol.displayNameToEnum(alcoholMap[position]!!)
        }.toMutableList()

        if(uiState.value.selectedItemList.size != 1){
            alcoholData.update {
                it.map { data ->
                    if(!data.isSelected){
                        data.copy(
                            hideAddBtn = false
                        )
                    } else {
                        data.copy()
                    }
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
            _event.emit(AlcoholSelectEvent.NavigateToAlcoholSelectDetail)
        }
    }

    fun cancelRecord() {
        viewModelScope.launch {
            RecordFormData.clear()
            _event.emit(AlcoholSelectEvent.NavigateToBack)
        }
    }

    fun finishRecord() {
        viewModelScope.launch {
            _event.emit(AlcoholSelectEvent.FinishRecord)
        }
    }

}