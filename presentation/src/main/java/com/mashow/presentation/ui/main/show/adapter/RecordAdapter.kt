package com.mashow.presentation.ui.main.show.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mashow.presentation.databinding.ItemAlcoholRecordBinding
import com.mashow.presentation.ui.main.show.model.UiRecordItem
import com.mashow.presentation.util.DefaultDiffUtil

class RecordAdapter() : ListAdapter<UiRecordItem, RecordViewHolder>(
    DefaultDiffUtil<UiRecordItem>()
) {

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        return RecordViewHolder(
            ItemAlcoholRecordBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }
}

class RecordViewHolder(private val binding: ItemAlcoholRecordBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: UiRecordItem) {
        binding.rvRecordPreview.adapter = RecordPreviewAdapter()
        binding.item = item
    }
}