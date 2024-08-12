package com.masshow.presentation.ui.main.record.food

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.masshow.presentation.R
import com.masshow.presentation.base.BaseFragment
import com.masshow.presentation.databinding.FragmentFoodRecordDetailBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FoodRecordDetailFragment :
    BaseFragment<FragmentFoodRecordDetailBinding>(R.layout.fragment_food_record_detail){

    private val viewModel: FoodRecordDetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        initEventObserve()
        addEditFood()
    }

    private fun initEventObserve() {
        repeatOnStarted {
            viewModel.event.collect {
                when (it) {
                    is FoodRecordDetailEvent.NavigateBack -> findNavController().navigateUp()
                    is FoodRecordDetailEvent.AddEditFood -> addEditFood()
                }
            }
        }
    }

    private fun addEditFood(){
        val newEditFood = LayoutInflater.from(requireContext()).inflate(R.layout.item_et_food, binding.layoutEditFood, false)
        val editFood = newEditFood.findViewById<EditText>(R.id.et_food)
        val deleteBtn = newEditFood.findViewById<ImageView>(R.id.btn_delete)

        editFood.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                editFood.setBackgroundResource(R.drawable.rect_darkgray2fill_graystroke_14radius)
                deleteBtn.visibility = View.VISIBLE
            } else {
                editFood.setBackgroundResource(R.drawable.rect_darkgrayfill_graystroke_14radius)
                deleteBtn.visibility = View.INVISIBLE
            }
        }

        deleteBtn.setOnClickListener {
            editFood.setText("")
        }

        binding.layoutEditFood.addView(newEditFood)
    }

}