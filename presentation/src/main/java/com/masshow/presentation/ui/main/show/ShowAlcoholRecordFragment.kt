package com.masshow.presentation.ui.main.show

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.masshow.presentation.R
import com.masshow.presentation.base.BaseFragment
import com.masshow.presentation.databinding.FragmentShowAlcoholRecordBinding

class ShowAlcoholRecordFragment: BaseFragment<FragmentShowAlcoholRecordBinding>(R.layout.fragment_show_alcohol_record) {

    private val args : ShowAlcoholRecordFragmentArgs by navArgs()
    val alcohol by lazy{args.alcohol}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showToastMessage(alcohol)
    }
}