package com.masshow.presentation.ui.intro.signup

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.masshow.presentation.R
import com.masshow.presentation.base.BaseFragment
import com.masshow.presentation.databinding.FragmentSignupBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupFragment : BaseFragment<FragmentSignupBinding>(R.layout.fragment_signup) {

    private val args: SignupFragmentArgs by navArgs()
    private val provider by lazy { args.provider }
    private val token by lazy { args.token }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}