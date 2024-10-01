package com.masshow.presentation.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


fun getTodayDateWithDay(): String {
    val calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("M월 dd일", Locale.getDefault())
    val dayFormat = SimpleDateFormat("EEEE", Locale.getDefault())

    val formattedDate = dateFormat.format(calendar.time)
    val formattedDay = dayFormat.format(calendar.time)

    return "$formattedDate $formattedDay"
}

