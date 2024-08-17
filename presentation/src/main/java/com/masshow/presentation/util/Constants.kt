package com.masshow.presentation.util

import com.masshow.presentation.R

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

    val alcoholAddBtnMap = hashMapOf<String, Int>(
        "소주" to R.drawable.btn_soju_added,
        "양주" to R.drawable.btn_liquor_added,
        "막걸리" to R.drawable.btn_makguli_added,
        "사케" to R.drawable.btn_sake_added,
        "맥주" to R.drawable.btn_beer_added,
        "와인" to R.drawable.btn_wine_added,
        "칵테일" to R.drawable.btn_cocktail_added,
        "하이볼" to R.drawable.btn_highball_added
    )
}