package com.masshow.presentation.ui.main.record.alchol

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.masshow.presentation.R
import com.masshow.presentation.base.BaseFragment
import com.masshow.presentation.databinding.FragmentAlcoholSelectBinding
import com.masshow.presentation.ui.main.MainViewModel
import com.masshow.presentation.ui.main.record.alchol.adapter.AlcoholSelectAdapter
import com.masshow.presentation.ui.main.record.alchol.adapter.SelectedAlcoholAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlcoholSelectFragment :
    BaseFragment<FragmentAlcoholSelectBinding>(R.layout.fragment_alcohol_select) {

        private val parentViewModel : MainViewModel by activityViewModels()
    private val viewModel: AlcoholSelectViewModel by viewModels()
    private var adapter: AlcoholSelectAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        initViewPager()
        initEventObserve()
        setBtnListener()
        adapter = AlcoholSelectAdapter()
        binding.vpAlcohol.adapter = adapter

        binding.rvSelectedAlcohol.itemAnimator = null
        binding.rvSelectedAlcohol.adapter = SelectedAlcoholAdapter()
        binding.vpAlcohol.registerOnPageChangeCallback(pageChangeCallback)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initViewPager() {
        repeatOnStarted {
            viewModel.alcoholData.collect {
                if (it.isNotEmpty()) {
                    adapter?.updateItem(it)
                }
            }
        }
    }

    private fun initEventObserve() {
        repeatOnStarted {
            viewModel.event.collect {
                when (it) {
                    is AlcoholSelectEvent.ChangeSelectedAlcohol -> {
                        adapter?.updateItem(viewModel.alcoholData.value)
                    }
                    is AlcoholSelectEvent.NavigateToAlcoholSelectDetail -> findNavController().toAlcoholDetail()
                    is AlcoholSelectEvent.NavigateToBack -> findNavController().navigateUp()
                    is AlcoholSelectEvent.FinishRecord -> parentViewModel.record()
                }
            }
        }

        repeatOnStarted {
            parentViewModel.finishRecord.collect{
                showToastMessage(it)
                findNavController().navigateUp()
            }
        }
    }

    private fun setBtnListener() {
        binding.btnLeft.setOnClickListener {
            val prevItem = binding.vpAlcohol.currentItem - 1

            if (prevItem >= 0) {
                binding.vpAlcohol.setCurrentItem(prevItem, true)
            }
        }

        binding.btnRight.setOnClickListener {
            val nextItem = binding.vpAlcohol.currentItem + 1

            if (nextItem < adapter!!.itemCount) {
                binding.vpAlcohol.setCurrentItem(nextItem, true)
            }
        }
    }

    private val pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {

            viewModel.setCurrentData(position)

            when (position) {
                0 -> binding.ivBackground.setImageResource(R.drawable.background_soju)
                1 -> binding.ivBackground.setImageResource(R.drawable.background_yangju)
                2 -> binding.ivBackground.setImageResource(R.drawable.background_makguli)
                3 -> binding.ivBackground.setImageResource(R.drawable.background_sake)
                4 -> binding.ivBackground.setImageResource(R.drawable.background_beer)
                5 -> binding.ivBackground.setImageResource(R.drawable.background_wine)
                6 -> binding.ivBackground.setImageResource(R.drawable.background_cocktail)
                7 -> binding.ivBackground.setImageResource(R.drawable.background_highball)
            }
        }

        override fun onPageSelected(position: Int) {}

        override fun onPageScrollStateChanged(state: Int) {}
    }

    fun NavController.toAlcoholDetail() {
        val action = AlcoholSelectFragmentDirections.actionAlcoholSelectFragmentToAlcoholDetailFragment()
        navigate(action)
    }

}