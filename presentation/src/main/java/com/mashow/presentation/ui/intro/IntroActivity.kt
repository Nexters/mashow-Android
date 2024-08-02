package com.mashow.presentation.ui.intro

import android.os.Bundle
import com.mashow.presentation.base.BaseActivity
import com.mashow.presentation.databinding.ActivityIntroBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroActivity: BaseActivity<ActivityIntroBinding>(ActivityIntroBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


}