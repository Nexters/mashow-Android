package com.nexters.presentation.ui.intro.login

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.nexters.presentation.R
import com.nexters.presentation.base.BaseFragment
import com.nexters.presentation.databinding.FragmentLoginBinding
import com.nexters.presentation.util.Constants.TAG
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    private val viewModel: LoginViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        initEventObserve()
    }

    private fun initEventObserve() {
        repeatOnStarted {
            viewModel.event.collect {
                when (it) {
                    is LoginEvent.GoogleLogin -> googleLogin()
                    is LoginEvent.KakaoLogin -> kakaoLogin()
                }
            }
        }
    }

    private fun kakaoLogin() {
        // 카카오톡 설치 확인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(requireContext())) {
            // 카카오톡 로그인
            UserApiClient.instance.loginWithKakaoTalk(requireContext()) { token, error ->
                // 로그인 실패 부분
                if (error != null) {
                    Log.e(TAG, "앱 로그인 실패 $error")
                    // 사용자가 취소
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }
                    // 다른 오류
                    else {
                        UserApiClient.instance.loginWithKakaoAccount(
                            requireContext(),
                            callback = kakaoEmailCb
                        ) // 카카오 이메일 로그인
                    }
                }
                // 로그인 성공 부분
                else if (token != null) {
                    // 카카오 로그인 성공 부분
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(
                requireContext(),
                callback = kakaoEmailCb
            ) // 카카오 이메일 로그인
        }
    }


    // 카카오톡 이메일 로그인 콜백
    private val kakaoEmailCb: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e(TAG, "이메일 로그인 실패 $error")
        } else if (token != null) {
            // 카카오 로그인 성공 부분
        }
    }

    fun googleLogin() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestProfile()
            .requestEmail()
            .build()
        val googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
        val signInIntent = googleSignInClient.signInIntent
        resultLauncher.launch(signInIntent)
    }

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

            if (result.resultCode == Activity.RESULT_OK) {
                // 로그인 유저정보 불러오기
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                val account = task.getResult(ApiException::class.java)

            }
        }
}