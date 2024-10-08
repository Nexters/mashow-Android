package com.masshow.presentation.util

import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
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

fun formatDate(input: String): String {
    val (data, dum) = input.split(".")
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
    val dateTime = LocalDateTime.parse(data, formatter)
    val outputFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
    return dateTime.format(outputFormatter)
}

fun formatDateTime(input: String): String {
    val (data, dum) = input.split(".")
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
    val dateTime = LocalDateTime.parse(data, formatter)
    val outputFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd hh:mm a")
    return dateTime.format(outputFormatter)
}

