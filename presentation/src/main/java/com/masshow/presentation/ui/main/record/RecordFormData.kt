package com.masshow.presentation.ui.main.record

object RecordFormData {
    var foods = listOf<String>()
    var selectedAlcoholMap = hashMapOf<String, MutableList<String>>()
    var rating: Int = 0
    var sideDishes = listOf<String>()
    var memo: String = ""
}