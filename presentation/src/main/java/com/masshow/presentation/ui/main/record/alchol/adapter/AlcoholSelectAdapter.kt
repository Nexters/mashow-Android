package com.masshow.presentation.ui.main.record.alchol.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.masshow.presentation.databinding.ItemAlcoholBinding
import com.masshow.presentation.ui.main.record.alchol.model.UiAlcoholSelectItem

class AlcoholSelectAdapter(private val data: List<UiAlcoholSelectItem>) :
    RecyclerView.Adapter<AlcoholSelectViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlcoholSelectViewHolder {
        return AlcoholSelectViewHolder(
            ItemAlcoholBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: AlcoholSelectViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

}

class AlcoholSelectViewHolder(private val binding: ItemAlcoholBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: UiAlcoholSelectItem) {
        binding.tvAlcoholText.setImageResource(item.alcoholText)
        binding.ivAlcohol.setImageResource(item.alcoholImage)
    }

}