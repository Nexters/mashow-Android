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
import com.masshow.presentation.ui.main.record.alchol.AlcoholSelectFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        initStateObserve()
        initEventObserve()
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
    }

    private fun initEventObserve(){
        repeatOnStarted {
            viewModel.event.collect{
                when(it){
                    is HomeEvent.NavigateToRecord -> findNavController().toAlcoholSelect()
                }
            }
        }
    }

    private fun NavController.toAlcoholSelect(){
        val action = HomeFragmentDirections.actionHomeFragmentToAlcoholSelectFragment()
        navigate(action)
    }

}