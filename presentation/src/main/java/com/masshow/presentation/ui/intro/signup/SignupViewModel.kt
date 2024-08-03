package com.masshow.presentation.ui.intro.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masshow.data.model.BaseState
import com.masshow.data.model.request.SignupRequest
import com.masshow.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class SignupEvent {
    data object NavigateToMain : SignupEvent()
}

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _event = MutableSharedFlow<SignupEvent>()
    val event: SharedFlow<SignupEvent> = _event.asSharedFlow()

    val nickname = MutableStateFlow("")
    private var authToken = ""
    private var authProvider = ""

    init {
        observeNick()
    }

    fun setInitData(provider: String, token: String) {
        authToken = token
        authProvider = provider
    }

    private fun observeNick() {
        nickname.onEach {
            // 닉네임 검증 등등
        }.launchIn(viewModelScope)
    }

    fun signup() {
        viewModelScope.launch {
            repository.signup(
                SignupRequest(
                    oAuthToken = authToken,
                    provider = authProvider,
                    nickname = nickname.value
                )
            ).let {
                when (it) {
                    is BaseState.Success -> {
                        it.data?.let{ data ->
                            repository.putAccessToken(data.accessToken)
                        }
                        _event.emit(SignupEvent.NavigateToMain)
                    }

                    is BaseState.Error -> {

                    }
                }
            }
        }
    }

}