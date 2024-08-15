package com.masshow.presentation.ui.intro.signup

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.masshow.presentation.R
import com.masshow.presentation.base.BaseFragment
import com.masshow.presentation.databinding.FragmentSignupBinding
import com.masshow.presentation.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupFragment : BaseFragment<FragmentSignupBinding>(R.layout.fragment_signup) {

    private val viewModel: SignupViewModel by viewModels()
    private val args: SignupFragmentArgs by navArgs()
    private val provider by lazy { args.provider }
    private val token by lazy { args.token }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        viewModel.setInitData(provider, token)
        binding.etNick.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                view.setBackgroundResource(R.drawable.rect_blackfill_graystroke_14radius)
            } else {
                view.setBackgroundResource(R.drawable.rect_nofill_graystroke_14radius)
            }
        }
        initObserveEvent()
    }

    private fun initObserveEvent() {
        repeatOnStarted {
            viewModel.event.collect {
                when (it) {
                    is SignUpEvent.NavigateToMain -> {
                        val intent = Intent(requireContext(), MainActivity::class.java)
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(intent)
                    }

                    is SignUpEvent.ShowToastMessage -> showToastMessage(it.msg)
                }
            }
        }
    }
}