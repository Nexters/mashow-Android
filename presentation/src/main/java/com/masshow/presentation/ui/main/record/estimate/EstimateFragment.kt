package com.masshow.presentation.ui.main.record.estimate

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup.LayoutParams
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.masshow.presentation.R
import com.masshow.presentation.base.BaseFragment
import com.masshow.presentation.databinding.FragmentEstimateBinding
import com.masshow.presentation.ui.main.record.RecordFormData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EstimateFragment : BaseFragment<FragmentEstimateBinding>(R.layout.fragment_estimate) {

    private val viewModel: EstimateViewModel by viewModels()

    private var initialY = 0f
    private var initialHeight = 0


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        initEventObserve()
        setSwipeListener()
    }

    private fun initEventObserve() {
        repeatOnStarted {
            viewModel.event.collect {
                when (it) {
                    is EstimateEvent.NavigateToHome -> findNavController().toHome()
                    is EstimateEvent.NavigateToFoodRecord -> findNavController().toFoodRecord()
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setSwipeListener() {
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
                        RecordFormData.rating = 1
                    } else if (point in notBadPoint..goodPoint) {
                        binding.ivUmm.visibility = View.INVISIBLE
                        binding.ivNotBad.visibility = View.VISIBLE
                        binding.ivGood.visibility = View.INVISIBLE
                        RecordFormData.rating = 2
                    } else if (point in goodPoint..veryGoodPoint) {
                        binding.ivNotBad.visibility = View.INVISIBLE
                        binding.ivGood.visibility = View.VISIBLE
                        binding.ivVeryGood.visibility = View.INVISIBLE
                        RecordFormData.rating = 3
                    } else if (point in veryGoodPoint..awesomePoint) {
                        binding.ivGood.visibility = View.INVISIBLE
                        binding.ivVeryGood.visibility = View.VISIBLE
                        binding.ivAwsome.visibility = View.INVISIBLE
                        RecordFormData.rating = 4
                    } else if (point >= awesomePoint) {
                        binding.ivVeryGood.visibility = View.INVISIBLE
                        binding.ivAwsome.visibility = View.VISIBLE
                        RecordFormData.rating = 5
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

    private fun NavController.toFoodRecord() {
        val action = EstimateFragmentDirections.actionEstimateFragmentToFoodRecordFragment()
        navigate(action)
    }

    private fun NavController.toHome() {
        val action = EstimateFragmentDirections.actionEstimateFragmentToHomeFragment()
        navigate(action)
    }

}