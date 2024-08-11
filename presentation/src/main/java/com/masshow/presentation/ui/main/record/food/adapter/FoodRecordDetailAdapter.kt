package com.masshow.presentation.ui.main.record.food.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.masshow.presentation.R
import com.masshow.presentation.databinding.ItemAddFoodBinding
import com.masshow.presentation.databinding.ItemEtFoodBinding
import com.masshow.presentation.ui.main.record.food.model.UiRecordFood
import com.masshow.presentation.util.Constants.ADD_FOOD
import com.masshow.presentation.util.Constants.EDIT_FOOD
import com.masshow.presentation.util.DefaultDiffUtil

class FoodRecordDetailAdapter :
    ListAdapter<UiRecordFood, RecyclerView.ViewHolder>(DefaultDiffUtil<UiRecordFood>()) {

    private var listener: FoodRecordInterface? = null

    fun setFoodRecordInterface(_listener: FoodRecordInterface) {
        listener = _listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            EDIT_FOOD -> {
                FoodRecordViewHolder(
                    ItemEtFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                )
            }

            ADD_FOOD -> {
                AddFoodViewHolder(
                    ItemAddFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                )
            }

            else -> throw IllegalArgumentException("Invalid ViewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItem(position).type) {
            EDIT_FOOD -> (holder as FoodRecordViewHolder).bind(getItem(position))
            ADD_FOOD -> (holder as AddFoodViewHolder).bind(getItem(position), listener)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).type
    }

}

class FoodRecordViewHolder(private val binding: ItemEtFoodBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: UiRecordFood) {
        binding.etFood.doOnTextChanged { text, start, before, count ->

        }

        binding.etFood.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                binding.etFood.setBackgroundResource(R.drawable.rect_darkgray2fill_graystroke_14radius)
                binding.btnDelete.visibility = View.VISIBLE
            } else {
                binding.etFood.setBackgroundResource(R.drawable.rect_darkgrayfill_graystroke_14radius)
                binding.btnDelete.visibility = View.INVISIBLE
            }
        }

        binding.btnDelete.setOnClickListener {
            binding.etFood.setText("")
        }
    }
}

class AddFoodViewHolder(private val binding: ItemAddFoodBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: UiRecordFood, listener: FoodRecordInterface?) {
        binding.btnAddFood.setOnClickListener {
            listener?.addFood()
        }
    }
}