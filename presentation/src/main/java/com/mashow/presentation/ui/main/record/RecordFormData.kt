package com.mashow.presentation.ui.main.record

import com.mashow.presentation.util.Alcohol

object RecordFormData {
    var selectedAlcoholDetailList = mutableListOf<Pair<Alcohol, MutableList<String>>>()
    var rating: Int = 3
    var sideDishes = listOf<String>()
    var memo: String = ""

    fun clear(){
        selectedAlcoholDetailList = mutableListOf()
        rating = 0
        sideDishes = listOf()
        memo = ""
    }
}