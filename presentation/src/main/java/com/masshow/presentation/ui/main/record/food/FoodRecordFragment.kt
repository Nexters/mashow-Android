package com.masshow.presentation.ui.main.record.food

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.masshow.presentation.R
import com.masshow.presentation.base.BaseFragment
import com.masshow.presentation.databinding.FragmentFoodRecordBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FoodRecordFragment : BaseFragment<FragmentFoodRecordBinding>(R.layout.fragment_food_record) {

    private val viewModel: FoodRecordViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        initEventObserve()
        binding.tvFoods.text = FoodRecordData.foods.joinToString()
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


}