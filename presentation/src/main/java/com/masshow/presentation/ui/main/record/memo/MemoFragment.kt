package com.masshow.presentation.ui.main.record.memo

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.masshow.presentation.R
import com.masshow.presentation.base.BaseFragment
import com.masshow.presentation.databinding.FragmentMemoBinding
import com.masshow.presentation.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MemoFragment : BaseFragment<FragmentMemoBinding>(R.layout.fragment_memo) {

    private val parentViewModel: MainViewModel by activityViewModels()
    private val viewModel: MemoViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        initEventObserve()
    }

    private fun initEventObserve() {
        repeatOnStarted {
            viewModel.events.collect {
                when (it) {
                    is MemoEvents.FinishRecord -> parentViewModel.finishRecord
                    is MemoEvents.NavigateToBack -> findNavController().navigateUp()
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

    private fun NavController.toHome() {
        val action = MemoFragmentDirections.actionMemoFragmentToHomeFragment()
        navigate(action)
    }
}