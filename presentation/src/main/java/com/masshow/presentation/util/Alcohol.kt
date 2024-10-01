package com.masshow.presentation.util

import com.masshow.presentation.R

enum class Alcohol(
    val displayName: String,
    val textResource: Int,
    val imageResource: Int,
    val btnResource: Int,
) {
    SOJU("소주", R.drawable.text_soju, R.drawable.image_soju, R.drawable.btn_soju_added),
    LIQUOR("양주", R.drawable.text_liquor, R.drawable.image_liquor, R.drawable.btn_liquor_added),
    SAKE("사케", R.drawable.text_sake, R.drawable.image_sake, R.drawable.btn_sake_added),
    BEER("맥주", R.drawable.text_beer, R.drawable.image_beer, R.drawable.btn_beer_added),
    HIGHBALL(
        "하이볼",
        R.drawable.text_highball,
        R.drawable.image_highball,
        R.drawable.btn_highball_added
    ),
    COCKTAIL(
        "칵테일",
        R.drawable.text_cocktail,
        R.drawable.image_cocktail,
        R.drawable.btn_cocktail_added
    ),
    WINE("와인", R.drawable.text_wine, R.drawable.image_wine, R.drawable.btn_wine_added),
    MAKGEOLLI(
        "막걸리",
        R.drawable.text_makguli,
        R.drawable.image_makguli,
        R.drawable.btn_makguli_added
    );

    companion object {
        fun displayNameToEnum(displayName: String) =
            entries.first { it.displayName.contains(displayName) }
    }
}
