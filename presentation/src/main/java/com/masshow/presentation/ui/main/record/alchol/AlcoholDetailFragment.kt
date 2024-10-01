package com.masshow.presentation.ui.main.record.alchol

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.masshow.presentation.R
import com.masshow.presentation.base.BaseFragment
import com.masshow.presentation.databinding.FragmentAlcoholDetailBinding
import com.masshow.presentation.ui.main.record.RecordFormData
import com.masshow.presentation.util.Alcohol
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlcoholDetailFragment :
    BaseFragment<FragmentAlcoholDetailBinding>(R.layout.fragment_alcohol_detail) {


    private val viewModel: AlcoholDetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        initEventObserve()
        RecordFormData.selectedAlcoholList.forEach {
            addAlcoholForm(it.first)
        }
    }

    private fun initEventObserve() {
        repeatOnStarted {
            viewModel.event.collect {
                when (it) {
                    is AlcoholDetailEvent.NavigateToBack -> findNavController().navigateUp()
                    is AlcoholDetailEvent.NavigateToEstimate -> {
                        findNavController().toEstimate()
                    }

                    is AlcoholDetailEvent.NavigateToHome -> findNavController().toHome()
                }
            }
        }
    }

    private fun addAlcoholForm(alcohol: Alcohol) {
        val newAlcoholForm = LayoutInflater.from(requireContext())
            .inflate(R.layout.item_layout_et_alcohol, binding.layoutEditAlcohol, false)

        val alcoholName = newAlcoholForm.findViewById<TextView>(R.id.tv_alcohol_name)
        val addBtn = newAlcoholForm.findViewById<TextView>(R.id.btn_add_alcohol)
        val layoutEditAlcohol = newAlcoholForm.findViewById<LinearLayout>(R.id.layout_edit_alcohol)

        alcoholName.text = alcohol.displayName
        var count = 0

        addBtn.setOnClickListener {
            count++
            viewModel.addCustomAlcoholName(alcohol)
            addEditAlcohol(layoutEditAlcohol, alcohol, count)
        }

        viewModel.addCustomAlcoholName(alcohol)
        addEditAlcohol(layoutEditAlcohol, alcohol, count)
        binding.layoutEditAlcohol.addView(newAlcoholForm)
    }

    private fun addEditAlcohol(layout: LinearLayout, alcohol: Alcohol, position: Int) {
        val newEditAlcohol =
            LayoutInflater.from(requireContext()).inflate(R.layout.item_et_food, layout, false)
        val editAlcohol = newEditAlcohol.findViewById<EditText>(R.id.et_food)
        val deleteBtn = newEditAlcohol.findViewById<ImageView>(R.id.btn_delete)

        editAlcohol.doOnTextChanged { detailName, _, _, _ ->
            viewModel.editCustomAlcoholName(alcohol, detailName.toString(), position)
        }

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

    private fun NavController.toEstimate() {
        val action = AlcoholDetailFragmentDirections.actionAlcoholDetailFragmentToEstimateFragment()
        navigate(action)
    }

    private fun NavController.toHome() {
        val action = AlcoholDetailFragmentDirections.actionAlcoholDetailFragmentToHomeFragment()
        navigate(action)
    }
}