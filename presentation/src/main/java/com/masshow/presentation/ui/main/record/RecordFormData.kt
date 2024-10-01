package com.masshow.presentation.ui.main.record

import com.masshow.presentation.util.Alcohol

object RecordFormData {
    var selectedAlcoholList = mutableListOf<Pair<Alcohol, MutableList<String>>>()
    var rating: Int = 0
    var sideDishes = listOf<String>()
    var memo: String = ""

    fun clear(){
        selectedAlcoholList = mutableListOf()
        rating = 0
        sideDishes = listOf()
        memo = ""
    }
}