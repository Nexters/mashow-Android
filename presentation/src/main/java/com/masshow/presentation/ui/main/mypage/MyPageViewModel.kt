package com.masshow.presentation.ui.main.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masshow.data.model.BaseState
import com.masshow.data.model.request.UserSimpleInfoQuery
import com.masshow.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class MyPageEvent {
    data object NavigateToBack : MyPageEvent()
    data object NavigateToLogin : MyPageEvent()
    data class ShowToastMessage(val msg: String) : MyPageEvent()
    data object ShowLoading : MyPageEvent()
    data object DismissLoading : MyPageEvent()
}

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _event = MutableSharedFlow<MyPageEvent>()
    val event: SharedFlow<MyPageEvent> = _event.asSharedFlow()

    fun logout() {
        viewModelScope.launch {
            authRepository.clear()
            _event.emit(MyPageEvent.ShowToastMessage("로그아웃 성공"))
            _event.emit(MyPageEvent.NavigateToLogin)
        }
    }

    fun withdrawal() {
        viewModelScope.launch {
            authRepository.getUserId()?.let { id ->
                authRepository.getNick()?.let { nick ->
                    _event.emit(MyPageEvent.ShowLoading)
                    authRepository.withdrawal(UserSimpleInfoQuery(id, nick)).let {
                        when (it) {
                            is BaseState.Success -> {
                                _event.emit(MyPageEvent.ShowToastMessage("회원 탈퇴 성공"))
                                _event.emit(MyPageEvent.NavigateToLogin)
                            }

                            is BaseState.Error -> {
                                _event.emit(MyPageEvent.ShowToastMessage(it.message))
                            }
                        }
                        _event.emit(MyPageEvent.DismissLoading)
                    }
                }
            }
        }
    }

    fun navigateToBack() {
        viewModelScope.launch {
            _event.emit(MyPageEvent.NavigateToBack)
        }
    }
}