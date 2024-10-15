package com.mashow.presentation.ui.main.record.memo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mashow.presentation.ui.main.record.RecordFormData
import com.mashow.presentation.util.getTodayDateWithDay
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class MemoEvents {
    data object FinishRecord : MemoEvents()
    data object NavigateToBack : MemoEvents()
}

@HiltViewModel
class MemoViewModel @Inject constructor(
) : ViewModel() {

    private val _events = MutableSharedFlow<MemoEvents>()
    val events: SharedFlow<MemoEvents> = _events.asSharedFlow()

    val memo = MutableStateFlow("")

    val date = getTodayDateWithDay()

    fun finishRecord() {
        viewModelScope.launch {
            RecordFormData.memo = memo.value
            _events.emit(MemoEvents.FinishRecord)
        }
    }

    fun navigateToBack() {
        viewModelScope.launch {
            _events.emit(MemoEvents.NavigateToBack)
        }
    }
}