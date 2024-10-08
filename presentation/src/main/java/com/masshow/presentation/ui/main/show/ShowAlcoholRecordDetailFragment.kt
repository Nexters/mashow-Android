package com.masshow.presentation.ui.main.show

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.masshow.presentation.R
import com.masshow.presentation.base.BaseFragment
import com.masshow.presentation.databinding.FragmentShowAlcoholRecordDetailBinding
import com.masshow.presentation.util.Alcohol
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShowAlcoholRecordDetailFragment :
    BaseFragment<FragmentShowAlcoholRecordDetailBinding>(R.layout.fragment_show_alcohol_record_detail) {

    private val viewModel: ShowAlcoholRecordDetailViewModel by viewModels()
    private val args: ShowAlcoholRecordDetailFragmentArgs by navArgs()
    private val id by lazy { args.id }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        viewModel.setId(id)
        initEventObserve()
        initStateObserve()
    }

    private fun initEventObserve() {
        repeatOnStarted {
            viewModel.event.collect {
                when (it) {
                    is ShowAlcoholRecordDetailEvent.NavigateToBack -> findNavController().navigateUp()
                    is ShowAlcoholRecordDetailEvent.ShowToastMessage -> showToastMessage(it.msg)
                    is ShowAlcoholRecordDetailEvent.ShowLoading -> showLoading(requireContext())
                    is ShowAlcoholRecordDetailEvent.DismissLoading -> dismissLoading()
                    is ShowAlcoholRecordDetailEvent.NavigateToHome -> findNavController().toHome()
                }
            }
        }
    }

    private fun initStateObserve() {
        repeatOnStarted {
            viewModel.rating.collect {
                val ratings = listOf(
                    binding.ivRaindrop1,
                    binding.ivRaindrop2,
                    binding.ivRaindrop3,
                    binding.ivRaindrop4,
                    binding.ivRaindrop5
                )

                ratings.forEachIndexed { index, imageView ->
                    if (index < 5 - it) {
                        imageView.visibility = View.GONE
                    } else {
                        imageView.visibility = View.VISIBLE
                    }
                }

            }
        }

        repeatOnStarted {
            viewModel.alcohol.collect {
                binding.ivBigCard.setImageResource(Alcohol.nameToEnum(it).cardResource)
            }
        }
    }

    private fun NavController.toHome() {
        val action =
            ShowAlcoholRecordDetailFragmentDirections.actionShowAlcoholRecordDetailFragmentToHomeFragment()
        navigate(action)
    }

}