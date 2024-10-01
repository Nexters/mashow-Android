package com.masshow.presentation.ui.main.record.alchol

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masshow.presentation.ui.main.record.RecordFormData
import com.masshow.presentation.util.Alcohol
import com.masshow.presentation.util.getTodayDateWithDay
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class AlcoholDetailEvent{
    data object NavigateToEstimate: AlcoholDetailEvent()
    data object NavigateToBack: AlcoholDetailEvent()
    data object NavigateToHome: AlcoholDetailEvent()
}

@HiltViewModel
class AlcoholDetailViewModel @Inject constructor() : ViewModel() {

    private val _event = MutableSharedFlow<AlcoholDetailEvent>()
    val event: SharedFlow<AlcoholDetailEvent> = _event.asSharedFlow()

    val date = getTodayDateWithDay()

    fun addCustomAlcoholName(alcohol: Alcohol){
        RecordFormData.selectedAlcoholList.forEach {
            if(it.first == alcohol) it.second.add("")
        }
    }

    fun editCustomAlcoholName(alcohol: Alcohol, detailName: String, position: Int){
        RecordFormData.selectedAlcoholList.forEach {
            if(it.first == alcohol){
                it.second[position] = detailName
            }
        }
    }

    fun navigateToEstimate(){
        viewModelScope.launch {
            _event.emit(AlcoholDetailEvent.NavigateToEstimate)
        }
    }

    fun navigateToBack(){
        viewModelScope.launch {
            _event.emit(AlcoholDetailEvent.NavigateToBack)
        }
    }

    fun cancelRecord(){
        viewModelScope.launch {
            RecordFormData.clear()
            _event.emit(AlcoholDetailEvent.NavigateToHome)
        }
    }
}