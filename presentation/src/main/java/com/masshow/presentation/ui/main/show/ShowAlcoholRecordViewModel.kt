package com.masshow.presentation.ui.main.show

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masshow.data.model.BaseState
import com.masshow.data.model.request.MonthlyRecordRequest
import com.masshow.data.model.request.PaginationData
import com.masshow.data.repository.AuthRepository
import com.masshow.data.repository.MainRepository
import com.masshow.presentation.ui.main.show.model.UiRecordAlcoholDetailNameItem
import com.masshow.presentation.ui.main.show.model.UiRecordChip
import com.masshow.presentation.ui.main.show.model.UiRecordItem
import com.masshow.presentation.util.Alcohol
import com.masshow.presentation.util.formatDate
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

data class ShowAlcoholRecordUiState(
    val percent: String = "",
    val page: Int = 1,
    val hasNext: Boolean = true,
    val recordAlcoholDetailList: List<UiRecordAlcoholDetailNameItem> = emptyList(),
    val recordPreviewList: List<UiRecordItem> = emptyList()
)

sealed class ShowAlcoholRecordEvent {
    data object NavigateToBack : ShowAlcoholRecordEvent()
    data class NavigateToDetail(val id: Long) : ShowAlcoholRecordEvent()
    data object NavigateToRecord: ShowAlcoholRecordEvent()
}

@HiltViewModel
class ShowAlcoholRecordViewModel @Inject constructor(
    private val repository: MainRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _event = MutableSharedFlow<ShowAlcoholRecordEvent>()
    val event: SharedFlow<ShowAlcoholRecordEvent> = _event.asSharedFlow()

    private val _uiState = MutableStateFlow(ShowAlcoholRecordUiState())
    val uiState: StateFlow<ShowAlcoholRecordUiState> = _uiState.asStateFlow()

    var selectedAlcohol = MutableStateFlow(Alcohol.SOJU)

    var nick = ""
    private var userId = 0L

    init {
        getUserInfo()
    }

    private fun getUserInfo() {
        viewModelScope.launch {
            nick = authRepository.getNick().toString()
            userId = authRepository.getUserId() ?: 0L
        }
    }

    fun changeAlcohol(alcohol: Alcohol) {
        selectedAlcohol.value = alcohol
        getStatistic(alcohol)
        getMonthlyRecord()
    }

    private fun getStatistic(alcohol: Alcohol) {
        viewModelScope.launch {
            repository.getAlcoholStatistic(listOf(alcohol.toString())).let {
                when (it) {
                    is BaseState.Success -> {
                        it.data?.let { data ->
                            _uiState.update { state ->
                                state.copy(
                                    percent = data.frequencyPercentage.toString() + "%",
                                    recordAlcoholDetailList = data.names.map { item ->
                                        UiRecordAlcoholDetailNameItem(
                                            item.name,
                                            item.count.toString(),
                                            selectedAlcohol.value.colorResource
                                        )
                                    }
                                )
                            }
                        }

                    }

                    is BaseState.Error -> {

                    }
                }
            }
        }
    }

    private fun getMonthlyRecord() {
        viewModelScope.launch {
            if (uiState.value.hasNext) {
                repository.getRecordMonthly(
                    MonthlyRecordRequest(
                        listOf(selectedAlcohol.value.toString()),
                        userId,
                        PaginationData(
                            uiState.value.page, 5
                        )
                    )
                ).let {
                    when (it) {
                        is BaseState.Success -> {
                            it.data?.let { data ->
                                _uiState.update { state ->
                                    state.copy(
                                        page = data.currentPageIndex + 1,
                                        hasNext = !data.isLastPage,
                                        recordPreviewList = data.contents.map { item ->
                                            UiRecordItem(
                                                date = "${item.year}년 ${item.month}월",
                                                count = item.histories.size.toString(),
                                                items = item.histories.map { history ->
                                                    UiRecordChip(
                                                        id = history.historyId,
                                                        date = formatDate(history.drankAt),
                                                        navigateToDetail = ::navigateToDetail
                                                    )
                                                }
                                            )
                                        }
                                    )
                                }
                            }
                        }

                        is BaseState.Error -> {

                        }
                    }
                }
            }
        }
    }

    fun navigateToBack() {
        viewModelScope.launch {
            _event.emit(ShowAlcoholRecordEvent.NavigateToBack)
        }
    }

    private fun navigateToDetail(id: Long) {
        viewModelScope.launch {
            _event.emit(ShowAlcoholRecordEvent.NavigateToDetail(id))
        }
    }

    fun navigateToRecord(){
        viewModelScope.launch {
            _event.emit(ShowAlcoholRecordEvent.NavigateToRecord)
        }
    }

}