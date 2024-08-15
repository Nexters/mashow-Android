package com.masshow.presentation.bindingadapters

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView


@BindingAdapter("nickLength")
fun bindNickLength(tv: TextView, count: Int) {
    tv.text = "${count}/6"
}