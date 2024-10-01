package com.masshow.presentation.ui.main.record.estimate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masshow.presentation.ui.main.record.RecordFormData
import com.masshow.presentation.util.getTodayDateWithDay
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class EstimateEvent {
    data object NavigateToFoodRecord : EstimateEvent()
    data object NavigateToHome : EstimateEvent()
    data object FinishRecord : EstimateEvent()
    data object NavigateToBack: EstimateEvent()
}

@HiltViewModel
class EstimateViewModel @Inject constructor() : ViewModel() {

    private val _event = MutableSharedFlow<EstimateEvent>()
    val event: SharedFlow<EstimateEvent> = _event.asSharedFlow()

    val date = getTodayDateWithDay()

    fun navigateToFoodRecord() {
        viewModelScope.launch {
            _event.emit(EstimateEvent.NavigateToFoodRecord)
        }
    }

    fun cancelRecord() {
        viewModelScope.launch {
            RecordFormData.clear()
            _event.emit(EstimateEvent.NavigateToHome)
        }
    }

    fun finishRecord() {
        viewModelScope.launch {
            _event.emit(EstimateEvent.FinishRecord)
        }
    }

    fun navigateToBack(){
        viewModelScope.launch {
            _event.emit(EstimateEvent.NavigateToBack)
        }
    }

}