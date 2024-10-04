package com.masshow.presentation.ui.main.home

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.masshow.presentation.R
import com.masshow.presentation.base.BaseFragment
import com.masshow.presentation.databinding.FragmentHomeBinding
import com.masshow.presentation.util.Alcohol
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        initStateObserve()
        initEventObserve()
        viewModel.getExistRecord()
    }

    private fun initStateObserve() {
        repeatOnStarted {
            viewModel.uiState.collect {
                if (it.isListView) {
                    binding.btnListView.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.lightgray
                        )
                    )
                    binding.btnCardView.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.lightgray30
                        )
                    )
                } else {
                    binding.btnListView.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.lightgray30
                        )
                    )
                    binding.btnCardView.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.lightgray
                        )
                    )
                }
            }
        }

        repeatOnStarted {
            viewModel.existRecordLiquor.collect {
                it.forEach { data ->
                    when (data) {
                        Alcohol.SOJU -> binding.btnSoju.alpha = 1F
                        Alcohol.LIQUOR -> binding.btnLiquor.alpha = 1F
                        Alcohol.SAKE -> binding.btnSake.alpha = 1F
                        Alcohol.BEER -> binding.btnBeer.alpha = 1F
                        Alcohol.HIGHBALL -> binding.btnHighball.alpha = 1F
                        Alcohol.COCKTAIL -> binding.btnCocktail.alpha = 1F
                        Alcohol.WINE -> binding.btnWine.alpha = 1F
                        Alcohol.MAKGEOLLI -> binding.btnMakgeolli.alpha = 1F
                    }
                }
            }
        }
    }

    private fun initEventObserve() {
        repeatOnStarted {
            viewModel.event.collect {
                when (it) {
                    is HomeEvent.NavigateToRecord -> findNavController().toAlcoholSelect()
                    is HomeEvent.NavigateToShowRecord -> findNavController().toShowAlcohol(it.alcohol)
                    is HomeEvent.ShowToastMessage -> showToastMessage(it.msg)
                    is HomeEvent.NavigateToMyPage -> findNavController().toMyPage()
                }
            }
        }
    }

    private fun NavController.toAlcoholSelect() {
        val action = HomeFragmentDirections.actionHomeFragmentToAlcoholSelectFragment()
        navigate(action)
    }

    private fun NavController.toShowAlcohol(alcohol: Alcohol) {
        val action = HomeFragmentDirections.actionHomeFragmentToShowAlcoholRecordFragment(alcohol)
        navigate(action)
    }

    private fun NavController.toMyPage() {
        val action = HomeFragmentDirections.actionHomeFragmentToMyPageFragment()
        navigate(action)
    }

}