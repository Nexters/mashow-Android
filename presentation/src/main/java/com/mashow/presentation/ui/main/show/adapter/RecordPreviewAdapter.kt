package com.mashow.presentation.ui.main.show.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mashow.presentation.databinding.ItemAlcoholRecordPreviewBinding
import com.mashow.presentation.ui.main.show.model.UiRecordChip
import com.mashow.presentation.util.DefaultDiffUtil

class RecordPreviewAdapter() : ListAdapter<UiRecordChip, RecordPreviewViewHolder>(
    DefaultDiffUtil<UiRecordChip>()
) {

    override fun onBindViewHolder(holder: RecordPreviewViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordPreviewViewHolder {
        return RecordPreviewViewHolder(
            ItemAlcoholRecordPreviewBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }
}

class RecordPreviewViewHolder(private val binding: ItemAlcoholRecordPreviewBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: UiRecordChip) {
        binding.tvDate.text = item.date
        binding.tvDate.setTextColor(ContextCompat.getColor(binding.root.context, item.color))
        binding.root.setOnClickListener {
            item.navigateToDetail(item.id)
        }
    }
}