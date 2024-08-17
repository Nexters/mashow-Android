package com.masshow.presentation.ui.main.record.alchol

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.masshow.presentation.R
import com.masshow.presentation.base.BaseFragment
import com.masshow.presentation.databinding.FragmentAlcoholSelectBinding
import com.masshow.presentation.ui.main.record.alchol.adapter.AlcoholSelectAdapter
import com.masshow.presentation.util.Constants.TAG
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlcoholSelectFragment :
    BaseFragment<FragmentAlcoholSelectBinding>(R.layout.fragment_alcohol_select) {

    private val viewModel: AlcoholSelectViewModel by viewModels()
    private var adapter: AlcoholSelectAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewPager()
        setBtnListener()
        binding.vpAlcohol.registerOnPageChangeCallback(pageChangeCallback)
    }

    private fun initViewPager() {
        repeatOnStarted {
            viewModel.uiState.collect {
                if (it.alcoholData.isNotEmpty()) {

                    adapter = AlcoholSelectAdapter(it.alcoholData)
                    binding.vpAlcohol!!.adapter = adapter
                }
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

    val pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            when(position){
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

}