package com.masshow.presentation.ui.main.record

object RecordFormData {
    var selectedAlcoholMap = listOf<Pair<String, MutableList<String>>>()
    var rating: Int = 0
    var sideDishes = listOf<String>()
    var memo: String = ""

    fun clear(){
        selectedAlcoholMap = listOf<Pair<String, MutableList<String>>>()
        rating = 0
        sideDishes = listOf<String>()
        memo = ""
    }
}