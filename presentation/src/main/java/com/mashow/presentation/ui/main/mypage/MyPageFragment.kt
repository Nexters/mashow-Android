package com.mashow.presentation.ui.main.mypage

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.kakao.sdk.user.UserApiClient
import com.mashow.presentation.R
import com.mashow.presentation.base.BaseFragment
import com.mashow.presentation.databinding.FragmentMypageBinding
import com.mashow.presentation.ui.intro.IntroActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageFragment : BaseFragment<FragmentMypageBinding>(R.layout.fragment_mypage) {

    private val viewModel: MyPageViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        initEventObserve()
    }

    private fun initEventObserve() {
        repeatOnStarted {
            viewModel.event.collect {
                when (it) {
                    is MyPageEvent.NavigateToBack -> findNavController().navigateUp()
                    is MyPageEvent.KakaoUnlink -> kakaoUnlink()
                    is MyPageEvent.GoogleUnlink -> googleLogout()
                    is MyPageEvent.ShowToastMessage -> showToastMessage(it.msg)
                    is MyPageEvent.ShowLoading -> showLoading(requireContext())
                    is MyPageEvent.DismissLoading -> dismissLoading()
                }
            }
        }
    }

    // 카카오 연결 끊기
    private fun kakaoUnlink() {

        UserApiClient.instance.unlink { error ->
            if (error != null) {
            } else {
                navigateToIntro()
            }
        }
    }

    private fun googleLogout() {

        val googleSignInClient = GoogleSignIn.getClient(
            requireActivity(), GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN
            ).build()
        )
        googleSignInClient.signOut().addOnCompleteListener {
            navigateToIntro()
        }
    }

    private fun navigateToIntro() {
        val intent = Intent(requireContext(), IntroActivity::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

}