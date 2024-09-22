package com.masshow.presentation.util

object Constants {
    const val TAG = "debugging"

    const val KAKAO = "KAKAO"
    const val GOOGLE = "GOOGLE"

    const val EDIT_FOOD = -1
    const val ADD_FOOD = 0

    val alcoholMap = hashMapOf<Int, String>(
        0 to "소주",
        1 to "양주",
        2 to "막걸리",
        3 to "사케",
        4 to "맥주",
        5 to "와인",
        6 to "칵테일",
        7 to "하이볼"
    )
}