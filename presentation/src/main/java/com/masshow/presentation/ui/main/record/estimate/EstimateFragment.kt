package com.masshow.presentation.ui.main.record.estimate

import android.animation.Animator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.masshow.presentation.R
import com.masshow.presentation.base.BaseFragment
import com.masshow.presentation.databinding.FragmentEstimateBinding
import com.masshow.presentation.ui.main.MainViewModel
import com.masshow.presentation.ui.main.record.RecordFormData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EstimateFragment : BaseFragment<FragmentEstimateBinding>(R.layout.fragment_estimate) {

    private val parentViewModel: MainViewModel by activityViewModels()
    private val viewModel: EstimateViewModel by viewModels()

    private var initialY = 0f
    private var swipingAction = SwipingAction.NO_SWIPE
    private var curRating = 0
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
                    is EstimateEvent.FinishRecord -> parentViewModel.record()
                    is EstimateEvent.NavigateToBack -> findNavController().navigateUp()
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

    @SuppressLint("ClickableViewAccessibility")
    private fun setSwipeListener() {
        binding.viewSwipable.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    initialY = event.y
                    true
                }

                MotionEvent.ACTION_MOVE -> {
                    val deltaY = event.y - initialY

                    swipingAction = if (deltaY > 0) SwipingAction.SWIPE_DOWN
                    else SwipingAction.SWIPE_UP

                    true
                }

                MotionEvent.ACTION_UP -> {

                    when (swipingAction) {
                        SwipingAction.SWIPE_UP -> {
                            if (curRating < 5) curRating++
                        }

                        SwipingAction.SWIPE_DOWN -> {
                            if (curRating > 0) curRating--
                        }

                        else -> {}
                    }

                    changeSwipeViewHeight()
                    swipingAction = SwipingAction.NO_SWIPE
                    true
                }

                else -> false
            }
        }
    }

    private fun changeSwipeViewHeight() {
        val ummPoint =
            binding.root.height - binding.tvUmm.y.toInt() - resources.getDimensionPixelSize(R.dimen.estimate_text_half_height)
        val notBadPoint =
            binding.root.height - binding.tvNotBad.y.toInt() - resources.getDimensionPixelSize(R.dimen.estimate_text_half_height)
        val goodPoint =
            binding.root.height - binding.tvGood.y.toInt() - resources.getDimensionPixelSize(R.dimen.estimate_text_half_height)
        val veryGoodPoint =
            binding.root.height - binding.tvVeryGood.y.toInt() - resources.getDimensionPixelSize(R.dimen.estimate_text_half_height)
        val awesomePoint =
            binding.root.height - binding.tvAwesome.y.toInt() - resources.getDimensionPixelSize(R.dimen.estimate_text_half_height)

        val currentHeight = binding.swipeView.height
        val targetHeight = when (curRating) {
            0 -> 0
            1 -> ummPoint
            2 -> notBadPoint
            3 -> goodPoint
            4 -> veryGoodPoint
            5 -> awesomePoint
            else -> 0
        }

        startViewHeightChangeAnimation(currentHeight, targetHeight)
    }

    private fun startViewHeightChangeAnimation(curHeight: Int, targetHeight: Int) {
        val animator = ValueAnimator.ofInt(curHeight, targetHeight)
        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationEnd(animation: Animator) {
                when (curRating) {
                    0 -> {
                        binding.ivUmm.visibility = View.INVISIBLE
                        RecordFormData.rating = 3
                    }

                    1 -> {
                        binding.ivUmm.visibility = View.VISIBLE
                        binding.ivNotBad.visibility = View.INVISIBLE
                        RecordFormData.rating = 1
                    }

                    2 -> {
                        binding.ivUmm.visibility = View.INVISIBLE
                        binding.ivNotBad.visibility = View.VISIBLE
                        binding.ivGood.visibility = View.INVISIBLE
                        RecordFormData.rating = 2
                    }

                    3 -> {
                        binding.ivNotBad.visibility = View.INVISIBLE
                        binding.ivGood.visibility = View.VISIBLE
                        binding.ivVeryGood.visibility = View.INVISIBLE
                        RecordFormData.rating = 3
                    }

                    4 -> {
                        binding.ivGood.visibility = View.INVISIBLE
                        binding.ivVeryGood.visibility = View.VISIBLE
                        binding.ivAwsome.visibility = View.INVISIBLE
                    }

                    5 -> {
                        binding.ivVeryGood.visibility = View.INVISIBLE
                        binding.ivAwsome.visibility = View.VISIBLE
                        RecordFormData.rating = 5
                    }
                }
            }

            override fun onAnimationRepeat(animation: Animator) {}
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationCancel(animation: Animator) {}
        })

        animator.duration = 150
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.addUpdateListener { animation ->
            val animatedHeight = animation.animatedValue as Int
            binding.swipeView.layoutParams.height = animatedHeight
            binding.swipeView.requestLayout()
        }
        animator.start()
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

enum class SwipingAction {
    SWIPE_DOWN,
    SWIPE_UP,
    NO_SWIPE
}
