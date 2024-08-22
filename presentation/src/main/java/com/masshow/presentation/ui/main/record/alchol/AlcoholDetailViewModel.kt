package com.masshow.presentation.ui.main.record.alchol

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AlcoholDetailViewModel @Inject constructor() : ViewModel() {

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
}