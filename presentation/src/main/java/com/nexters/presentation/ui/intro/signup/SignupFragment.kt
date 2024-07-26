package com.nexters.presentation.ui.intro.signup

import android.os.Bundle
import android.view.View
import com.nexters.presentation.R
import com.nexters.presentation.base.BaseFragment
import com.nexters.presentation.databinding.FragmentSignupBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupFragment: BaseFragment<FragmentSignupBinding>(R.layout.fragment_signup)  {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}