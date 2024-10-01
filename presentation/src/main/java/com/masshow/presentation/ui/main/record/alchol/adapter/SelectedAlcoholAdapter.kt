package com.masshow.presentation.ui.main.record.alchol.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.masshow.presentation.databinding.ItemSelectedAlcoholBinding
import com.masshow.presentation.ui.main.record.alchol.AlcoholSelectViewModel
import com.masshow.presentation.ui.main.record.alchol.model.UiSelectedAlcoholItem
import com.masshow.presentation.util.DefaultDiffUtil

class SelectedAlcoholAdapter() : ListAdapter<UiSelectedAlcoholItem, SelectedAlcoholViewHolder>(
    DefaultDiffUtil<UiSelectedAlcoholItem>()
) {

    override fun onBindViewHolder(holder: SelectedAlcoholViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectedAlcoholViewHolder {
        return SelectedAlcoholViewHolder(
            ItemSelectedAlcoholBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }
}

class SelectedAlcoholViewHolder(private val binding: ItemSelectedAlcoholBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: UiSelectedAlcoholItem) {
        AlcoholSelectViewModel.alcoholMap[item.position]?.let {
            binding.tvAlcohol.text = it
        }
        binding.btnDelete.setOnClickListener {
            item.deleteSelect(item.position)
        }
    }
}