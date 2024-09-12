package com.masshow.presentation.ui.main.record.estimate

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup.LayoutParams
import androidx.constraintlayout.widget.ConstraintSet.Motion
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.masshow.presentation.R
import com.masshow.presentation.base.BaseFragment
import com.masshow.presentation.databinding.FragmentEstimateBinding
import com.masshow.presentation.util.Constants.TAG
import kotlin.math.max
import kotlin.math.min

class EstimateFragment : BaseFragment<FragmentEstimateBinding>(R.layout.fragment_estimate) {

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

        binding.btnNext.setOnClickListener {
            findNavController().toFoodRecord()
        }

        binding.viewSwipable.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    initialY = event.rawY
                    initialHeight = binding.swipeView.height
                    true
                }

                MotionEvent.ACTION_MOVE -> {

                    binding.swipeView.layoutParams =
                        (binding.swipeView.layoutParams as LayoutParams).apply {
                            height = (v.height - event.rawY).toInt() + 400
                        }

                    val ummPoint = binding.root.height - binding.tvUmm.y.toInt()
                    val notBadPoint = binding.root.height - binding.tvNotBad.y.toInt()
                    val goodPoint = binding.root.height - binding.tvGood.y.toInt()
                    val veryGoodPoint = binding.root.height - binding.tvVeryGood.y.toInt()
                    val awesomePoint = binding.root.height - binding.tvAwesome.y.toInt()

                    val point = (v.height - event.rawY).toInt() + 400

                    if (point in ummPoint..notBadPoint) {
                        binding.ivUmm.visibility = View.VISIBLE
                        binding.ivNotBad.visibility = View.INVISIBLE
                    } else if (point in notBadPoint..goodPoint) {
                        binding.ivUmm.visibility = View.INVISIBLE
                        binding.ivNotBad.visibility = View.VISIBLE
                        binding.ivGood.visibility = View.INVISIBLE
                    } else if (point in goodPoint..veryGoodPoint) {
                        binding.ivNotBad.visibility = View.INVISIBLE
                        binding.ivGood.visibility = View.VISIBLE
                        binding.ivVeryGood.visibility = View.INVISIBLE
                    } else if (point in veryGoodPoint..awesomePoint) {
                        binding.ivGood.visibility = View.INVISIBLE
                        binding.ivVeryGood.visibility = View.VISIBLE
                        binding.ivAwsome.visibility = View.INVISIBLE
                    } else if (point >= awesomePoint) {
                        binding.ivVeryGood.visibility = View.INVISIBLE
                        binding.ivAwsome.visibility = View.VISIBLE
                    } else if (point < binding.root.height - binding.tvUmm.y.toInt()) {
                        binding.ivUmm.visibility = View.INVISIBLE
                    }

                    binding.swipeView.requestLayout()
                    true
                }

                MotionEvent.ACTION_UP -> true
                else -> false
            }
        }

    }

    private fun NavController.toFoodRecord(){
        val action = EstimateFragmentDirections.actionEstimateFragmentToFoodRecordFragment()
        navigate(action)
    }


}