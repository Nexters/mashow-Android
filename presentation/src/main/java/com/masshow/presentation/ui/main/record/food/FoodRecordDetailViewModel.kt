package com.masshow.presentation.ui.main.record.food

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masshow.presentation.util.Constants.EDIT_FOOD
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

sealed class FoodRecordDetailEvent {
    data object NavigateBack : FoodRecordDetailEvent()
    data object AddEditFood: FoodRecordDetailEvent()
}

data class FoodRecordUiState(
    val editFood : List<String> = emptyList()
)

@HiltViewModel
class FoodRecordDetailViewModel @Inject constructor() : ViewModel() {

    private val _event = MutableSharedFlow<FoodRecordDetailEvent>()
    val event: SharedFlow<FoodRecordDetailEvent> = _event.asSharedFlow()

    private val _uiState = MutableStateFlow(FoodRecordUiState())
    val uiState: StateFlow<FoodRecordUiState> = _uiState.asStateFlow()

    fun navigateBack() {
        viewModelScope.launch {
            _event.emit(FoodRecordDetailEvent.NavigateBack)
        }
    }

    fun addEditFood() {
        viewModelScope.launch {
            _event.emit(FoodRecordDetailEvent.AddEditFood)
        }
    }

}