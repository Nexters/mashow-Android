package com.masshow.presentation.ui.main.record.food

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.masshow.presentation.R
import com.masshow.presentation.base.BaseFragment
import com.masshow.presentation.databinding.FragmentFoodRecordBinding
import com.masshow.presentation.ui.main.record.RecordFormData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FoodRecordFragment : BaseFragment<FragmentFoodRecordBinding>(R.layout.fragment_food_record) {

    private val viewModel: FoodRecordViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        initEventObserve()
        RecordFormData.foods.forEach {
            addFood(it)
        }
    }

    private fun initEventObserve() {
        repeatOnStarted {
            viewModel.event.collect {
                when (it) {
                    is FoodRecordEvent.NavigateToFoodRecordDetail -> findNavController().toFoodRecordDetail()
                }
            }
        }
    }

    private fun NavController.toFoodRecordDetail() {
        val action =
            FoodRecordFragmentDirections.actionFoodRecordFragmentToFoodRecordDetailFragment()
        navigate(action)
    }

    private fun addFood(text: String) {
        val newFood = LayoutInflater.from(requireContext())
            .inflate(R.layout.item_food, binding.layoutFood, false)
        val food = newFood.findViewById<TextView>(R.id.tv_text)

        food.text = text

        binding.layoutFood.addView(newFood)
    }


}