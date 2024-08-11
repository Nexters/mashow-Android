package com.masshow.presentation.ui.main.record.food

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.masshow.presentation.R
import com.masshow.presentation.base.BaseFragment
import com.masshow.presentation.databinding.FragmentFoodRecordDetailBinding
import com.masshow.presentation.ui.main.record.food.adapter.FoodRecordDetailAdapter
import com.masshow.presentation.ui.main.record.food.adapter.FoodRecordInterface
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FoodRecordDetailFragment :
    BaseFragment<FragmentFoodRecordDetailBinding>(R.layout.fragment_food_record_detail), FoodRecordInterface {

    private val viewModel: FoodRecordDetailViewModel by viewModels()
    private var adapter: FoodRecordDetailAdapter ?= null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FoodRecordDetailAdapter()
        adapter?.setFoodRecordInterface(this)
        binding.vm = viewModel
        binding.rvEtFood.itemAnimator = null
        binding.rvEtFood.adapter = adapter
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

    override fun addFood() {
        viewModel.addEditFood()
    }


}