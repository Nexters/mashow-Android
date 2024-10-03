package com.masshow.presentation.ui.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masshow.data.model.BaseState
import com.masshow.data.repository.MainRepository
import com.masshow.presentation.util.Alcohol
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

data class HomeUiState(
    val isListView: Boolean = true,
)

sealed class HomeEvent {
    data object NavigateToRecord : HomeEvent()
    data class NavigateToShowRecord(val alcohol: Alcohol) : HomeEvent()
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    val existRecordLiquor = MutableStateFlow<List<Alcohol>>(emptyList())

    private val _event = MutableSharedFlow<HomeEvent>()
    val event: SharedFlow<HomeEvent> = _event.asSharedFlow()

    fun getExistRecord() {
        viewModelScope.launch {
            repository.recordExistLiquor().let {
                when (it) {
                    is BaseState.Success -> {
                        it.data?.let { data ->
                            existRecordLiquor.update {
                                data.liquorHistoryTypes.map{ item ->
                                    Alcohol.nameToEnum(item)
                                }
                            }
                        }
                    }

                    is BaseState.Error -> {

                    }
                }
            }
        }
    }

    fun selectListView() {
        _uiState.update { state ->
            state.copy(
                isListView = true
            )
        }
    }

    fun selectCardView() {
        _uiState.update { state ->
            state.copy(
                isListView = false
            )
        }
    }

    fun navigateToRecord() {
        viewModelScope.launch {
            _event.emit(HomeEvent.NavigateToRecord)
        }
    }

    fun navigateToShowRecord(alcohol: Alcohol) {
        viewModelScope.launch {
            if (existRecordLiquor.value.contains(alcohol)) {
                _event.emit(HomeEvent.NavigateToShowRecord(alcohol))
            }
        }
    }

}