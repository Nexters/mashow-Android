package com.masshow.presentation.ui.main.record.alchol.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.masshow.presentation.R
import com.masshow.presentation.databinding.ItemAlcoholBinding
import com.masshow.presentation.ui.main.record.alchol.model.UiAlcoholSelectItem
import com.masshow.presentation.util.Constants.TAG

class AlcoholSelectAdapter() :
    RecyclerView.Adapter<AlcoholSelectViewHolder>() {

    private var data: List<UiAlcoholSelectItem> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlcoholSelectViewHolder {
        return AlcoholSelectViewHolder(
            ItemAlcoholBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: AlcoholSelectViewHolder, position: Int) {
        holder.bind(data[position])
    }


    @SuppressLint("NotifyDataSetChanged")
    fun updateItem(list: List<UiAlcoholSelectItem>) {
        Log.d(TAG, "update")
        data = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = data.size

}

class AlcoholSelectViewHolder(private val binding: ItemAlcoholBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: UiAlcoholSelectItem) {
        if (item.isSelected) {
            binding.btnAddAlcohol.setImageResource(item.alcohol.btnResource)
        } else {
            binding.btnAddAlcohol.setImageResource(R.drawable.btn_add_alcohol)
        }

        binding.btnAddAlcohol.setOnClickListener {
            if (item.isSelected) {
                item.delete(adapterPosition)
            }else{
                item.adding(adapterPosition)
            }
        }

        if(item.hideAddBtn){
            binding.btnAddAlcohol.visibility = View.GONE
        } else {
            binding.btnAddAlcohol.visibility = View.VISIBLE
        }

        binding.tvAlcoholText.setImageResource(item.alcohol.textResource)
        binding.ivAlcohol.setImageResource(item.alcohol.imageResource)
    }

}