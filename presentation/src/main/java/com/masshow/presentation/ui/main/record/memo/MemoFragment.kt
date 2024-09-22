package com.masshow.presentation.ui.main.record.memo

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.masshow.presentation.R
import com.masshow.presentation.base.BaseFragment
import com.masshow.presentation.databinding.FragmentMemoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MemoFragment : BaseFragment<FragmentMemoBinding>(R.layout.fragment_memo){

    private val viewModel: MemoViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        initEventObserve()
    }

    private fun initEventObserve(){
        repeatOnStarted {
            viewModel.events.collect{
                when(it){
                    is MemoEvents.FinishRecord -> {
                        showToastMessage("음주 기록 완료!")
                        findNavController().toHome()
                    }
                }
            }
        }
    }

    private fun NavController.toHome(){
        val action = MemoFragmentDirections.actionMemoFragmentToHomeFragment()
        navigate(action)
    }
}