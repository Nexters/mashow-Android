package com.mashow.presentation.bindingadapters

import android.widget.TextView
import androidx.databinding.BindingAdapter


@BindingAdapter("nickLength")
fun bindNickLength(tv: TextView, count: Int) {
    tv.text = "${count}/6"
}