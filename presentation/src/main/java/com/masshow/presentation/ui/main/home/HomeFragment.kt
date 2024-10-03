package com.masshow.presentation.ui.main.home

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.masshow.presentation.R
import com.masshow.presentation.base.BaseFragment
import com.masshow.presentation.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels()

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
                        "SOJU" -> binding.btnSoju.alpha = 1F
                        "LIQUOR" -> binding.btnLiquor.alpha = 1F
                        "SAKE" -> binding.btnSake.alpha = 1F
                        "BEER" -> binding.btnBeer.alpha = 1F
                        "HIGHBALL" -> binding.btnHighball.alpha = 1F
                        "COCKTAIL" -> binding.btnCocktail.alpha = 1F
                        "WINE" -> binding.btnWine.alpha = 1F
                        "MAKGEOLLI" -> binding.btnMakgeolli.alpha = 1F
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
                }
            }
        }
    }

    private fun NavController.toAlcoholSelect() {
        val action = HomeFragmentDirections.actionHomeFragmentToAlcoholSelectFragment()
        navigate(action)
    }

    private fun NavController.toShowAlcohol(alcohol: String){
        val action = HomeFragmentDirections.actionHomeFragmentToShowAlcoholRecordFragment(alcohol)
        navigate(action)
    }

}