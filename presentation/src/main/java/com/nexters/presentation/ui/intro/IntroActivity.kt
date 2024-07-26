package com.nexters.presentation.ui.intro

import android.os.Bundle
import com.nexters.presentation.base.BaseActivity
import com.nexters.presentation.databinding.ActivityIntroBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroActivity: BaseActivity<ActivityIntroBinding>(ActivityIntroBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


}