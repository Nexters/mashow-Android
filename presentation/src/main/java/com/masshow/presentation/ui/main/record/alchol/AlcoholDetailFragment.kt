package com.masshow.presentation.ui.main.record.alchol

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.navArgs
import com.masshow.presentation.R
import com.masshow.presentation.base.BaseFragment
import com.masshow.presentation.databinding.FragmentAlcoholDetailBinding
import com.masshow.presentation.util.Constants.TAG

class AlcoholDetailFragment: BaseFragment<FragmentAlcoholDetailBinding>(R.layout.fragment_alcohol_detail) {

    private val args : AlcoholDetailFragmentArgs by navArgs()
    val alcoholList by lazy{args.alcohollist}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        alcoholList.forEach {
            Log.d(TAG,it)
        }

    }
}