package com.masshow.presentation.ui.main.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class MyPageEvent {
    data object NavigateToBack : MyPageEvent()
    data object NavigateToLogin : MyPageEvent()
}

@HiltViewModel
class MyPageViewModel @Inject constructor() : ViewModel() {

    private val _event = MutableSharedFlow<MyPageEvent>()
    val event: SharedFlow<MyPageEvent> = _event.asSharedFlow()

    fun logout() {

    }

    fun withdrawal() {

    }

    fun navigateToBack() {
        viewModelScope.launch {
            _event.emit(MyPageEvent.NavigateToBack)
        }
    }
}