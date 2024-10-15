package com.mashow.presentation.util

import com.mashow.presentation.R

enum class Alcohol(
    val displayName: String,
    val textResource: Int,
    val imageResource: Int,
    val btnResource: Int,
    val colorResource: Int,
    val cardResource: Int
) {
    SOJU(
        "소주",
        R.drawable.text_soju,
        R.drawable.image_soju,
        R.drawable.btn_soju_added,
        R.color.soju,
        R.drawable.big_card_soju
    ),
    LIQUOR(
        "양주",
        R.drawable.text_liquor,
        R.drawable.image_liquor,
        R.drawable.btn_liquor_added,
        R.color.liquor,
        R.drawable.big_card_liquor
    ),
    SAKE(
        "사케",
        R.drawable.text_sake,
        R.drawable.image_sake,
        R.drawable.btn_sake_added,
        R.color.makgeolli,
        R.drawable.big_card_makgeolli
    ),
    BEER(
        "맥주",
        R.drawable.text_beer,
        R.drawable.image_beer,
        R.drawable.btn_beer_added,
        R.color.sake,
        R.drawable.big_card_beer
    ),
    HIGHBALL(
        "하이볼",
        R.drawable.text_highball,
        R.drawable.image_highball,
        R.drawable.btn_highball_added,
        R.color.highball,
        R.drawable.big_card_highball
    ),
    COCKTAIL(
        "칵테일",
        R.drawable.text_cocktail,
        R.drawable.image_cocktail,
        R.drawable.btn_cocktail_added,
        R.color.sake,
        R.drawable.big_card_sake
    ),
    WINE(
        "와인",
        R.drawable.text_wine,
        R.drawable.image_wine,
        R.drawable.btn_wine_added,
        R.color.wine,
        R.drawable.big_card_wine
    ),
    MAKGEOLLI(
        "막걸리",
        R.drawable.text_makgeolli,
        R.drawable.image_makgeolli,
        R.drawable.btn_makgeolli_added, R.color.makgeolli,
        R.drawable.big_card_makgeolli
    );

    companion object {
        fun displayNameToEnum(displayName: String) =
            entries.first { it.displayName.contains(displayName) }

        fun nameToEnum(name: String) =
            entries.first { it.toString().contains(name) }
    }
}
