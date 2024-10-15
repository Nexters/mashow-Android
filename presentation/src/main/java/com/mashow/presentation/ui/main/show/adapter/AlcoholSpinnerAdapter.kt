package com.mashow.presentation.ui.main.show.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.annotation.LayoutRes
import com.mashow.presentation.databinding.ItemSpinnerItemBinding
import com.mashow.presentation.util.Alcohol

class AlcoholSpinnerAdapter(
    context: Context,
    @LayoutRes private val resId: Int,
    private val items: List<Alcohol>
) : ArrayAdapter<Alcohol>(context, resId, items) {

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = ItemSpinnerItemBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        binding.tvMenu.text = items[position].displayName
        return binding.root
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = ItemSpinnerItemBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        binding.tvMenu.text = items[position].displayName
        return binding.root
    }

    override fun getCount(): Int = items.size

}