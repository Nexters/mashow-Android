package com.mashow.presentation.ui.intro.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mashow.data.model.BaseState
import com.mashow.data.model.request.LoginRequest
import com.mashow.data.repository.AuthRepository
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
            ).let{
                when(it){
                    is BaseState.Success -> {
                        _event.emit(LoginEvent.NavigateToSignup)
                    }

                    is BaseState.Error -> {

                    }
                }
            }

        }
    }

}