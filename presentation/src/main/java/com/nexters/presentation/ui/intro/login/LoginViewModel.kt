package com.nexters.presentation.ui.intro.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject



sealed class LoginEvent{
    data object KakaoLogin: LoginEvent()
    data object GoogleLogin: LoginEvent()
}

@HiltViewModel
class LoginViewModel @Inject constructor(): ViewModel() {

    private val _event = MutableSharedFlow<LoginEvent>()
    val event: SharedFlow<LoginEvent> = _event.asSharedFlow()


    fun kakaoLogin(){
        viewModelScope.launch {
            _event.emit(LoginEvent.KakaoLogin)
        }
    }

    fun googleLogin(){
        viewModelScope.launch {
            _event.emit(LoginEvent.GoogleLogin)
        }
    }

}