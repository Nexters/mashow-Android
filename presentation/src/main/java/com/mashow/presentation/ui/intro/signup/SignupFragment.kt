package com.mashow.presentation.ui.intro.signup

import android.os.Bundle
import android.view.View
import com.mashow.presentation.R
import com.mashow.presentation.base.BaseFragment
import com.mashow.presentation.databinding.FragmentSignupBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupFragment: BaseFragment<FragmentSignupBinding>(R.layout.fragment_signup)  {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}