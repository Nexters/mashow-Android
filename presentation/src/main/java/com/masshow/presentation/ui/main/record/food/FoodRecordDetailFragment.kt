package com.masshow.presentation.ui.main.record.food

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.masshow.presentation.R
import com.masshow.presentation.base.BaseFragment
import com.masshow.presentation.databinding.FragmentFoodRecordDetailBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FoodRecordDetailFragment :
    BaseFragment<FragmentFoodRecordDetailBinding>(R.layout.fragment_food_record_detail) {

    private val viewModel: FoodRecordDetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        initEventObserve()
    }

    private fun initEventObserve() {
        repeatOnStarted {
            viewModel.event.collect {
                when (it) {
                    is FoodRecordDetailEvent.NavigateBack -> findNavController().navigateUp()
                }
            }
        }
    }


}