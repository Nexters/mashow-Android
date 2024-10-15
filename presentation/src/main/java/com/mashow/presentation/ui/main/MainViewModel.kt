package com.mashow.presentation.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mashow.data.model.BaseState
import com.mashow.data.model.request.LiquorItem
import com.mashow.data.model.request.MemoItem
import com.mashow.data.model.request.RecordRequest
import com.mashow.data.model.request.SideDishItem
import com.mashow.data.repository.MainRepository
import com.mashow.presentation.ui.main.record.RecordFormData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class MainEvent {
    data object HideKeyboard: MainEvent()
    data object ShowKeyboard: MainEvent()
    data object ShowLoading: MainEvent()
    data object DismissLoading: MainEvent()
}

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _event = MutableSharedFlow<MainEvent>()
    val event: SharedFlow<MainEvent> = _event.asSharedFlow()

    val finishRecord = MutableSharedFlow<String>()

    fun record() {
        viewModelScope.launch {
            _event.emit(MainEvent.ShowLoading)
            repository.record(
                RecordRequest(
                    liquors = RecordFormData.selectedAlcoholDetailList.map {
                        LiquorItem(
                            it.first.toString(),
                            it.second.filter { data -> data.isNotBlank() }
                        )
                    },
                    memos = listOf(MemoItem(description = RecordFormData.memo)),
                    rating = RecordFormData.rating,
                    sideDishes = RecordFormData.sideDishes.map {
                        SideDishItem(it)
                    }
                )
            ).let {
                when (it) {
                    is BaseState.Success -> {
                        RecordFormData.clear()
                        finishRecord.emit("기록을 완료했습니다")
                    }

                    is BaseState.Error -> {
                        RecordFormData.clear()
                        finishRecord.emit("기록하기 실패")
                    }
                }

                _event.emit(MainEvent.DismissLoading)
            }
        }
    }

    fun hideKeyboard(){
        viewModelScope.launch {
            _event.emit(MainEvent.HideKeyboard)
        }
    }

    fun showKeyboard(){
        viewModelScope.launch {
            _event.emit(MainEvent.ShowKeyboard)
        }
    }
}