package com.masshow.presentation.ui.main.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class HomeUiState(
    val isListView : Boolean = true
)

@HiltViewModel
class HomeViewModel @Inject constructor(): ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()


    fun selectListView(){
        _uiState.update { state ->
            state.copy(
                isListView = true
            )
        }
    }

    fun selectCardView(){
        _uiState.update { state ->
            state.copy(
                isListView = false
            )
        }
    }

}