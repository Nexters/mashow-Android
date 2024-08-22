package com.masshow.presentation.ui.main.record.alchol

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class AlcoholDetailEvent{
    data object NavigateToEstimate: AlcoholDetailEvent()
    data object NavigateToBack: AlcoholDetailEvent()
}

@HiltViewModel
class AlcoholDetailViewModel @Inject constructor() : ViewModel() {

    private val _event = MutableSharedFlow<AlcoholDetailEvent>()
    val event: SharedFlow<AlcoholDetailEvent> = _event.asSharedFlow()

    val selectedAlcoholMap = hashMapOf<String, MutableList<String>>()

    fun setSelectedAlcohol(name: String) {
        selectedAlcoholMap[name] = mutableListOf()
    }

    fun addCustomAlcoholName(name: String){
        selectedAlcoholMap[name]?.add("")
    }

    fun editCustomAlcoholName(name: String, detailName: String, position: Int){
        selectedAlcoholMap[name]?.let{ list ->
            list[position] = detailName
        }
    }

    fun navigateToEstimate(){
        viewModelScope.launch {
            _event.emit(AlcoholDetailEvent.NavigateToEstimate)
        }
    }

    fun navigateToBack(){
        viewModelScope.launch {
            _event.emit(AlcoholDetailEvent.NavigateToBack)
        }
    }
}