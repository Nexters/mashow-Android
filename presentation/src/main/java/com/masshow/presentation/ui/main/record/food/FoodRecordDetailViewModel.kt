package com.masshow.presentation.ui.main.record.food

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class FoodRecordDetailEvent{
    data object NavigateBack: FoodRecordDetailEvent()
}

@HiltViewModel
class FoodRecordDetailViewModel @Inject constructor(): ViewModel() {

    private val _event = MutableSharedFlow<FoodRecordDetailEvent>()
    val event: SharedFlow<FoodRecordDetailEvent> = _event.asSharedFlow()

    fun navigateBack(){
        viewModelScope.launch {
            _event.emit(FoodRecordDetailEvent.NavigateBack)
        }
    }

}