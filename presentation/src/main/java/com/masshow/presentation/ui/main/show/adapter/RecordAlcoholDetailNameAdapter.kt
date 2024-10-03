package com.masshow.presentation.ui.main.show.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.masshow.presentation.databinding.ItemAlcoholChipBinding
import com.masshow.presentation.ui.main.show.model.UiRecordAlcoholDetailNameItem
import com.masshow.presentation.util.DefaultDiffUtil

class RecordAlcoholDetailNameAdapter() : ListAdapter<UiRecordAlcoholDetailNameItem, RecordAlcoholDetailNameViewHolder>(
    DefaultDiffUtil<UiRecordAlcoholDetailNameItem>()
) {

    override fun onBindViewHolder(holder: RecordAlcoholDetailNameViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordAlcoholDetailNameViewHolder {
        return RecordAlcoholDetailNameViewHolder(
            ItemAlcoholChipBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }
}

class RecordAlcoholDetailNameViewHolder(private val binding: ItemAlcoholChipBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: UiRecordAlcoholDetailNameItem) {
        binding.tvCount.setTextColor(ContextCompat.getColor(binding.root.context, item.color))
        binding.tvCount.text = item.count
        binding.tvName.text = item.name
    }
}