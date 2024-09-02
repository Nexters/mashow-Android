package com.masshow.presentation.ui.main.record.estimate

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup.LayoutParams
import androidx.constraintlayout.widget.ConstraintSet.Motion
import com.masshow.presentation.R
import com.masshow.presentation.base.BaseFragment
import com.masshow.presentation.databinding.FragmentEstimateBinding
import com.masshow.presentation.util.Constants.TAG
import kotlin.math.max
import kotlin.math.min

class EstimateFragment: BaseFragment<FragmentEstimateBinding>(R.layout.fragment_estimate) {

    private var initialY = 0f
    private var initialHeight = 0

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//
//        val initialHeightDp = 200
//        val displayMetrics = resources.displayMetrics
//        initialHeight = (initialHeightDp * displayMetrics.density).toInt()
//
//        binding.viewSwipable.layoutParams = binding.viewSwipable.layoutParams.apply{
//            height = initialHeight
//        }

        binding.viewSwipable.setOnTouchListener { v, event ->
            when(event.action){
                MotionEvent.ACTION_DOWN -> {
                    initialY = event.rawY
                    initialHeight = binding.swipeView.height
                    true
                }
                MotionEvent.ACTION_MOVE -> {
                    Log.d(TAG,event.rawY.toString())
                    binding.swipeView.layoutParams = (binding.swipeView.layoutParams as LayoutParams).apply {
                        height = (v.height - event.rawY).toInt() + 400
                    }
                    binding.swipeView.requestLayout()
                    true
                }
                MotionEvent.ACTION_UP -> true
                else -> false
            }
        }

    }


























}