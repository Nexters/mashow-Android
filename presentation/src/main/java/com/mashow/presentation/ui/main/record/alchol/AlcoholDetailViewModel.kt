package com.mashow.presentation.ui.main.record.alchol

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mashow.presentation.ui.main.record.RecordFormData
import com.mashow.presentation.util.Alcohol
import com.mashow.presentation.util.getTodayDateWithDay
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class AlcoholDetailEvent {
    data object NavigateToEstimate : AlcoholDetailEvent()
    data object NavigateToBack : AlcoholDetailEvent()
    data object NavigateToHome : AlcoholDetailEvent()
    data object FinishRecord : AlcoholDetailEvent()
}

data class AlcoholDetailPair(
    var id: Int,
    var name: String
)

@HiltViewModel
class AlcoholDetailViewModel @Inject constructor() : ViewModel() {

    private val _event = MutableSharedFlow<AlcoholDetailEvent>()
    val event: SharedFlow<AlcoholDetailEvent> = _event.asSharedFlow()

    val date = getTodayDateWithDay()

    val selectedAlcoholMap = hashMapOf<Alcohol, MutableList<AlcoholDetailPair>>()

    fun addAlcoholCategory(alcohol: Alcohol){
        selectedAlcoholMap[alcohol] = mutableListOf()
    }

    fun addCustomAlcoholName(alcohol: Alcohol, id: Int) {
        selectedAlcoholMap[alcohol]?.add(AlcoholDetailPair(id, ""))
    }

    fun editCustomAlcoholName(alcohol: Alcohol, detailName: String, id: Int) {
        selectedAlcoholMap[alcohol]?.forEach {
            if (it.id == id) it.name = detailName
        }
    }

    fun deleteCustomAlcoholName(alcohol: Alcohol, id: Int) {
        selectedAlcoholMap[alcohol]?.filter {
            it.id != id
        }
    }

    fun navigateToEstimate() {
        viewModelScope.launch {
            RecordFormData.selectedAlcoholDetailList = selectedAlcoholMap.map {
                Pair(it.key, it.value.map { data -> data.name }.toMutableList())
            }.toMutableList()
            _event.emit(AlcoholDetailEvent.NavigateToEstimate)
        }
    }

    fun navigateToBack() {
        viewModelScope.launch {
            _event.emit(AlcoholDetailEvent.NavigateToBack)
        }
    }

    fun cancelRecord() {
        viewModelScope.launch {
            RecordFormData.clear()
            _event.emit(AlcoholDetailEvent.NavigateToHome)
        }
    }

    fun finishRecord() {
        viewModelScope.launch {
            RecordFormData.selectedAlcoholDetailList = selectedAlcoholMap.map {
                Pair(it.key, it.value.map { data -> data.name }.toMutableList())
            }.toMutableList()
            _event.emit(AlcoholDetailEvent.FinishRecord)
        }
    }

}