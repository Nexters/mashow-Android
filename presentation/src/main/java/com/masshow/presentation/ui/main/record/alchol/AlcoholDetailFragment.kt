package com.masshow.presentation.ui.main.record.alchol

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import com.masshow.presentation.R
import com.masshow.presentation.base.BaseFragment
import com.masshow.presentation.databinding.FragmentAlcoholDetailBinding

class AlcoholDetailFragment :
    BaseFragment<FragmentAlcoholDetailBinding>(R.layout.fragment_alcohol_detail) {

    private val args: AlcoholDetailFragmentArgs by navArgs()
    val alcoholList by lazy { args.alcohollist }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        alcoholList.forEach {
            addAlcoholForm(it)
        }

    }

    private fun addAlcoholForm(name: String) {
        val newAlcoholForm = LayoutInflater.from(requireContext())
            .inflate(R.layout.item_layout_et_alcohol, binding.layoutEditAlcohol, false)

        val alcoholName = newAlcoholForm.findViewById<TextView>(R.id.tv_alcohol_name)
        val addBtn = newAlcoholForm.findViewById<TextView>(R.id.btn_add_alcohol)
        val layoutEditAlcohol = newAlcoholForm.findViewById<LinearLayout>(R.id.layout_edit_alcohol)

        alcoholName.text = name

        addBtn.setOnClickListener {
            addEditAlcohol(layoutEditAlcohol)
        }

        addEditAlcohol(layoutEditAlcohol)

        binding.layoutEditAlcohol.addView(newAlcoholForm)
    }

    private fun addEditAlcohol(layout: LinearLayout) {
        val newEditAlcohol =
            LayoutInflater.from(requireContext()).inflate(R.layout.item_et_food, layout, false)
        val editAlcohol = newEditAlcohol.findViewById<EditText>(R.id.et_food)
        val deleteBtn = newEditAlcohol.findViewById<ImageView>(R.id.btn_delete)

        editAlcohol.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                editAlcohol.setBackgroundResource(R.drawable.rect_darkgray2fill_graystroke_14radius)
                deleteBtn.visibility = View.VISIBLE
            } else {
                editAlcohol.setBackgroundResource(R.drawable.rect_darkgrayfill_graystroke_14radius)
                deleteBtn.visibility = View.INVISIBLE
            }
        }

        deleteBtn.setOnClickListener {
            editAlcohol.setText("")
        }

        layout.addView(newEditAlcohol)
    }
}