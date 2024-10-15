package com.mashow.presentation.ui.intro.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mashow.data.model.BaseState
import com.mashow.data.model.request.SignupRequest
import com.mashow.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class SignUpEvent {
    data object NavigateToMain : SignUpEvent()
    data class ShowToastMessage(val msg: String) : SignUpEvent()
    data object ShowLoading : SignUpEvent()
    data object DismissLoading : SignUpEvent()
}

data class SignUpUiState(
    val warningState: Boolean = false
)

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _event = MutableSharedFlow<SignUpEvent>()
    val event: SharedFlow<SignUpEvent> = _event.asSharedFlow()

    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()

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
            if (it.matches("^[a-z0-9ㄱ-ㅣ가-힣]*\$".toRegex())) {
                _uiState.update { state ->
                    state.copy(
                        warningState = false
                    )
                }
            } else {
                _uiState.update { state ->
                    state.copy(
                        warningState = true
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun signup() {
        viewModelScope.launch {
            _event.emit(SignUpEvent.ShowLoading)
            repository.signup(
                SignupRequest(
                    oAuthToken = authToken,
                    provider = authProvider,
                    nickname = nickname.value
                )
            ).let {
                when (it) {
                    is BaseState.Success -> {
                        it.data?.let { data ->
                            repository.putAccessToken(data.accessToken)
                            repository.putUserId(data.userId)
                            repository.putNick(data.nickname)
                            repository.putLoginType(authProvider)
                        }
                        _event.emit(SignUpEvent.ShowToastMessage("회원가입 성공"))
                        _event.emit(SignUpEvent.NavigateToMain)
                    }

                    is BaseState.Error -> {
                        repository.deleteAccessToken()
                        repository.deleteNick()
                        repository.deleteUserId()
                        repository.deleteLoginType()
                        _event.emit(SignUpEvent.ShowToastMessage(it.message))
                    }
                }
                _event.emit(SignUpEvent.DismissLoading)
            }
        }
    }

    fun deleteNick() {
        nickname.value = ""
    }

}