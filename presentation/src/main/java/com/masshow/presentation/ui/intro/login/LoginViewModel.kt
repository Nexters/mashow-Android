package com.masshow.presentation.ui.intro.login

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
    data class NavigateToSignup(val token: String, val provider: String) : LoginEvent()
    data object NavigateToMain : LoginEvent()
    data class ShowToastMessage(val msg: String) : LoginEvent()
    data object ShowLoading : LoginEvent()
    data object DismissLoading : LoginEvent()
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
        viewModelScope.launch {
            _event.emit(LoginEvent.ShowLoading)
            repository.login(
                LoginRequest(provider, token)
            ).let {
                when (it) {
                    is BaseState.Success -> {
                        it.data?.let { data ->
                            repository.putAccessToken(data.accessToken)
                            repository.putUserId(data.userId)
                            repository.putNick(data.nickname)
                        }
                        if (it.code == 100) {
                            // 신규회원
                            _event.emit(LoginEvent.NavigateToSignup(token, provider))
                        } else {
                            _event.emit(LoginEvent.ShowToastMessage("로그인 성공"))
                            _event.emit(LoginEvent.NavigateToMain)
                        }
                    }

                    is BaseState.Error -> {
                        repository.deleteAccessToken()
                        repository.deleteNick()
                        repository.deleteUserId()
                        _event.emit(LoginEvent.ShowToastMessage(it.message))
                    }
                }

                _event.emit(LoginEvent.DismissLoading)
            }

        }
    }

}