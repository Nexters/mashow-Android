package com.mashow.presentation.ui.main.record.alchol

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.mashow.presentation.R
import com.mashow.presentation.base.BaseFragment
import com.mashow.presentation.databinding.FragmentAlcoholDetailBinding
import com.mashow.presentation.ui.main.MainViewModel
import com.mashow.presentation.ui.main.record.RecordFormData
import com.mashow.presentation.util.Alcohol
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlcoholDetailFragment :
    BaseFragment<FragmentAlcoholDetailBinding>(R.layout.fragment_alcohol_detail) {

    private val parentViewModel: MainViewModel by activityViewModels()
    private val viewModel: AlcoholDetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        initEventObserve()
        RecordFormData.selectedAlcoholDetailList.forEach {
            viewModel.addAlcoholCategory(it.first)
            addAlcoholForm(it.first)
        }
        binding.layoutScroll.setOnClickListener {
            parentViewModel.hideKeyboard()
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
                    is AlcoholDetailEvent.FinishRecord -> parentViewModel.record()
                }
            }
        }

        repeatOnStarted {
            parentViewModel.finishRecord.collect {
                showToastMessage(it)
                findNavController().toHome()
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
        var id = 0

        addBtn.setOnClickListener {
            id++
            viewModel.addCustomAlcoholName(alcohol, id)
            addEditAlcohol(layoutEditAlcohol, alcohol, id)
        }

        viewModel.addCustomAlcoholName(alcohol, id)
        addEditAlcohol(layoutEditAlcohol, alcohol, id)
        binding.layoutEditAlcohol.addView(newAlcoholForm)
    }

    private fun addEditAlcohol(layout: LinearLayout, alcohol: Alcohol, id: Int) {
        val newEditAlcohol =
            LayoutInflater.from(requireContext()).inflate(R.layout.item_et_detail, layout, false)
        val editAlcohol = newEditAlcohol.findViewById<EditText>(R.id.et_detail)
        val deleteBtn = newEditAlcohol.findViewById<ImageView>(R.id.btn_delete)
        val eraseBtn = newEditAlcohol.findViewById<ImageView>(R.id.btn_erase)
        val newPadding = resources.getDimensionPixelSize(R.dimen.edit_detail_padding)
        val oldPadding = resources.getDimensionPixelSize(R.dimen.edit_detail_focus_padding)

        editAlcohol.doOnTextChanged { detailName, _, _, _ ->
            viewModel.editCustomAlcoholName(alcohol, detailName.toString(), id)
        }

        editAlcohol.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                parentViewModel.showKeyboard()
                editAlcohol.setPadding(oldPadding, oldPadding, oldPadding, oldPadding)
                editAlcohol.setBackgroundResource(R.drawable.rect_darkgray2fill_graystroke_14radius)
                deleteBtn.visibility = View.INVISIBLE
                eraseBtn.visibility = View.VISIBLE
            } else {
                editAlcohol.setPadding(newPadding, oldPadding, oldPadding, oldPadding)
                editAlcohol.setBackgroundResource(R.drawable.rect_darkgrayfill_graystroke_14radius)
                deleteBtn.visibility = View.VISIBLE
                eraseBtn.visibility = View.INVISIBLE
            }
        }

        editAlcohol.requestFocus()

        eraseBtn.setOnClickListener {
            editAlcohol.setText("")
        }

        deleteBtn.setOnClickListener {
            layout.removeView(newEditAlcohol)
            viewModel.deleteCustomAlcoholName(alcohol, id)
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