package com.masshow.presentation.ui.main.show.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.masshow.presentation.databinding.ItemAlcoholRecordPreviewBinding
import com.masshow.presentation.ui.main.show.model.UiRecordChip
import com.masshow.presentation.util.DefaultDiffUtil

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
        binding.root.setOnClickListener {
            item.navigateToDetail(item.id)
        }
    }
}