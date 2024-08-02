package com.mashow.presentation.ui.splash

import android.content.Intent
import android.os.Bundle
import com.mashow.presentation.base.BaseActivity
import com.mashow.presentation.databinding.ActivitySplashBinding
import com.mashow.presentation.ui.intro.IntroActivity
import com.mashow.presentation.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        repeatOnStarted {
            delay(2000)
//            viewModel.checkLoginType()
            toIntroActivity()
        }
//        repeatOnStarted {
//            viewModel.event.collect {
//                when (it) {
//                    is SplashUiEvent.NavigateToIntro -> toIntroActivity()
//                    is SplashUiEvent.NavigateToMain -> toMainActivity()
//                }
//            }
//        }
    }

    private fun toMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun toIntroActivity() {
        val intent = Intent(this, IntroActivity::class.java)
        startActivity(intent)
        finish()
    }
}