package com.masshow.presentation.ui.main.record.food

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class FoodRecordEvent{
    data object NavigateToFoodRecordDetail: FoodRecordEvent()
}

@HiltViewModel
class FoodRecordViewModel @Inject constructor(): ViewModel() {

    private val _event = MutableSharedFlow<FoodRecordEvent>()
    val event : SharedFlow<FoodRecordEvent> = _event.asSharedFlow()

    fun navigateToFoodRecord(){
        viewModelScope.launch {
            _event.emit(FoodRecordEvent.NavigateToFoodRecordDetail)
        }
    }

}