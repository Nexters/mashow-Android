package com.mashow.presentation.ui.main.show

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mashow.data.model.BaseState
import com.mashow.data.repository.MainRepository
import com.mashow.presentation.util.Alcohol
import com.mashow.presentation.util.formatDateTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ShowAlcoholRecordDetailUiState(
    val memo: String = "",
    val foods: String = "",
    val alcohols: String = "",
    val date: String = "",
    val showView: Boolean = false
)

sealed class ShowAlcoholRecordDetailEvent {
    data object NavigateToBack : ShowAlcoholRecordDetailEvent()
    data class ShowToastMessage(val msg: String) : ShowAlcoholRecordDetailEvent()
    data object ShowLoading : ShowAlcoholRecordDetailEvent()
    data object DismissLoading : ShowAlcoholRecordDetailEvent()
    data object NavigateToHome: ShowAlcoholRecordDetailEvent()
}

@HiltViewModel
class ShowAlcoholRecordDetailViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ShowAlcoholRecordDetailUiState())
    val uiState: StateFlow<ShowAlcoholRecordDetailUiState> = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<ShowAlcoholRecordDetailEvent>()
    val event: SharedFlow<ShowAlcoholRecordDetailEvent> = _event.asSharedFlow()

    private var historyId: Long = 0

    val rating = MutableStateFlow(5)
    val alcohol = MutableStateFlow("")

    fun setId(id: Long) {
        historyId = id
        getRecordDetail()
    }

    private fun getRecordDetail() {
        viewModelScope.launch {
            _event.emit(ShowAlcoholRecordDetailEvent.ShowLoading)
            repository.getRecordDetail(historyId).let {
                when (it) {
                    is BaseState.Success -> {
                        it.data?.let { data ->
                            _uiState.update { state ->
                                state.copy(
                                    date = formatDateTime(data.drankAt),
                                    memo = if (data.memos.isNotEmpty()) data.memos.first().description else "",
                                    foods = if (data.sideDishes.isNotEmpty()) data.sideDishes.first().names else "",
                                    alcohols = data.liquors.joinToString(" ") { item ->
                                        val alcohol =
                                            Alcohol.nameToEnum(item.liquorType).displayName
                                        item.details.joinToString(" ") { item2 ->
                                            alcohol + "_" + item2.names
                                        }
                                    }
                                )
                            }

                            rating.value = data.rating
                            alcohol.value = data.liquors.first().liquorType
                        }

                    }

                    is BaseState.Error -> {
                        _event.emit(ShowAlcoholRecordDetailEvent.ShowToastMessage(it.message))
                    }
                }

                delay(50)
                _uiState.update { state ->
                    state.copy(
                        showView = true
                    )
                }

                _event.emit(ShowAlcoholRecordDetailEvent.DismissLoading)
            }
        }
    }

    fun deleteRecord() {
        viewModelScope.launch {
            repository.deleteRecord(historyId).let {
                when (it) {
                    is BaseState.Success -> {
                        _event.emit(ShowAlcoholRecordDetailEvent.ShowToastMessage("기록 삭제 성공"))
                        _event.emit(ShowAlcoholRecordDetailEvent.NavigateToHome)
                    }

                    is BaseState.Error -> {
                        _event.emit(ShowAlcoholRecordDetailEvent.ShowToastMessage(it.message))
                    }
                }
            }
        }
    }

    fun navigateToBack() {
        viewModelScope.launch {
            _event.emit(ShowAlcoholRecordDetailEvent.NavigateToBack)
        }
    }


}