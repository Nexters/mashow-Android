package com.masshow.presentation.ui.main

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import com.masshow.presentation.base.BaseActivity
import com.masshow.presentation.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initEventObserve()
    }

    private fun initEventObserve() {
        repeatOnStarted {
            viewModel.event.collect {
                when (it) {
                    is MainEvent.HideKeyboard -> hideKeyboard()
                    is MainEvent.ShowKeyboard -> showKeyboard()
                    is MainEvent.ShowLoading -> showLoading()
                    is MainEvent.DismissLoading -> dismissLoading()
                }
            }
        }
    }

    private fun hideKeyboard() {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        currentFocus?.let {
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
            it.clearFocus()
        }
    }

    private fun showKeyboard() {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        currentFocus?.let {
            inputMethodManager.showSoftInput(it, InputMethodManager.SHOW_IMPLICIT)
        }
    }
}