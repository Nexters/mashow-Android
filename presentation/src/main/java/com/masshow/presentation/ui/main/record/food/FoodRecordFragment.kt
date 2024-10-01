package com.masshow.presentation.ui.main.record.food

import android.os.Bundle
import android.util.Log
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
import com.masshow.presentation.R
import com.masshow.presentation.base.BaseFragment
import com.masshow.presentation.databinding.FragmentFoodRecordBinding
import com.masshow.presentation.ui.main.MainViewModel
import com.masshow.presentation.ui.main.record.RecordFormData
import com.masshow.presentation.util.Constants.TAG
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FoodRecordFragment : BaseFragment<FragmentFoodRecordBinding>(R.layout.fragment_food_record) {

    private val parentViewModel: MainViewModel by activityViewModels()
    private val viewModel: FoodRecordViewModel by viewModels()

    private var editTextId = -1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        initEventObserve()
        CoroutineScope(Dispatchers.Main).launch {
            RecordFormData.sideDishes.forEach {
                editTextId++
                addFood(it)
                delay(600)
            }
        }
    }

    private fun initEventObserve() {
        repeatOnStarted {
            viewModel.event.collect {
                when (it) {
                    is FoodRecordEvent.NavigateToFoodRecordDetail -> findNavController().toFoodRecordDetail()
                    is FoodRecordEvent.NavigateToMemo -> findNavController().toMemo()
                    is FoodRecordEvent.NavigateToHome -> findNavController().toHome()
                    is FoodRecordEvent.FinishRecord -> parentViewModel.finishRecord
                    is FoodRecordEvent.NavigateToBack -> findNavController().navigateUp()
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

    private fun NavController.toFoodRecordDetail() {
        val action =
            FoodRecordFragmentDirections.actionFoodRecordFragmentToFoodRecordDetailFragment()
        navigate(action)
    }

    private fun addFood(text: String) {

        val newFood = if (editTextId % 2 == 0) {
            LayoutInflater.from(requireContext())
                .inflate(R.layout.item_food, binding.layoutFood, false)
        } else {
            LayoutInflater.from(requireContext())
                .inflate(R.layout.item_food_right, binding.layoutFood, false)
        }

        val food = newFood.findViewById<TextView>(R.id.tv_text)

        food.text = text

        newFood.translationY = -70f

        binding.layoutFood.addView(newFood, 0)

        newFood.animate()
            .translationY(0f)
            .setDuration(500)
            .start()
    }

    private fun NavController.toMemo() {
        val action = FoodRecordFragmentDirections.actionFoodRecordFragmentToMemoFragment()
        navigate(action)
    }

    private fun NavController.toHome() {
        val action = FoodRecordFragmentDirections.actionFoodRecordFragmentToHomeFragment()
        navigate(action)
    }


}