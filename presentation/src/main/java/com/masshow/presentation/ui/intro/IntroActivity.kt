package com.masshow.presentation.ui.intro

import android.os.Bundle
import com.masshow.presentation.base.BaseActivity
import com.masshow.presentation.databinding.ActivityIntroBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroActivity: BaseActivity<ActivityIntroBinding>(ActivityIntroBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


}