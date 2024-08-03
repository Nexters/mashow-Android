package com.masshow.presentation.ui.intro.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masshow.data.model.BaseState
import com.masshow.data.model.request.LoginRequest
import com.masshow.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed class LoginEvent {
    data object KakaoLogin : LoginEvent()
    data object GoogleLogin : LoginEvent()
    data object NavigateToSignup : LoginEvent()
    data object NavigateToMain : LoginEvent()
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _event = MutableSharedFlow<LoginEvent>()
    val event: SharedFlow<LoginEvent> = _event.asSharedFlow()


    fun kakaoLogin() {
        viewModelScope.launch {
            _event.emit(LoginEvent.KakaoLogin)
        }
    }

    fun googleLogin() {
        viewModelScope.launch {
            _event.emit(LoginEvent.GoogleLogin)
        }
    }

    fun login(token: String, provider: String) {
        Log.d("debugging", token)
        viewModelScope.launch {
            repository.login(
                LoginRequest(provider, token)
            ).let {
                when (it) {
                    is BaseState.Success -> {
                        if (it.code == 100) {
                            // 신규회원
                            _event.emit(LoginEvent.NavigateToSignup)
                        } else{
                            _event.emit(LoginEvent.NavigateToMain)
                        }
                    }

                    is BaseState.Error -> {

                    }
                }
            }

        }
    }

}