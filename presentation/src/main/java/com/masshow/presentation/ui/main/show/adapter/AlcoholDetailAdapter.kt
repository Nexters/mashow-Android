package com.masshow.presentation.ui.main.show.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.masshow.presentation.databinding.ItemAlcoholChipBinding
import com.masshow.presentation.ui.main.show.model.UiAlcoholDetailItem
import com.masshow.presentation.util.Constants.TAG
import com.masshow.presentation.util.DefaultDiffUtil

class AlcoholDetailAdapter() : ListAdapter<UiAlcoholDetailItem, AlcoholDetailViewHolder>(
    DefaultDiffUtil<UiAlcoholDetailItem>()
) {

    override fun onBindViewHolder(holder: AlcoholDetailViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlcoholDetailViewHolder {
        return AlcoholDetailViewHolder(
            ItemAlcoholChipBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }
}

class AlcoholDetailViewHolder(private val binding: ItemAlcoholChipBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: UiAlcoholDetailItem) {
        binding.tvCount.setTextColor(ContextCompat.getColor(binding.root.context, item.color))
        binding.tvCount.text = item.count
        binding.tvName.text = item.name
    }
}