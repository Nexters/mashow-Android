package com.masshow.presentation.ui.main.show

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.masshow.presentation.R
import com.masshow.presentation.base.BaseFragment
import com.masshow.presentation.databinding.FragmentShowAlcoholRecordDetailBinding

class ShowAlcoholRecordDetailFragment : BaseFragment<FragmentShowAlcoholRecordDetailBinding>(R.layout.fragment_show_alcohol_record_detail){

    private val viewModel : ShowAlcoholRecordDetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}