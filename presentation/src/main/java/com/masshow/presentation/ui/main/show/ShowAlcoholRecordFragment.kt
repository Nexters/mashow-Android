package com.masshow.presentation.ui.main.show

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.masshow.presentation.R
import com.masshow.presentation.base.BaseFragment
import com.masshow.presentation.databinding.FragmentShowAlcoholRecordBinding
import com.masshow.presentation.ui.main.home.HomeViewModel
import com.masshow.presentation.ui.main.show.adapter.RecordAlcoholDetailNameAdapter
import com.masshow.presentation.ui.main.show.adapter.AlcoholSpinnerAdapter
import com.masshow.presentation.ui.main.show.adapter.RecordAdapter
import com.masshow.presentation.util.Alcohol
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShowAlcoholRecordFragment :
    BaseFragment<FragmentShowAlcoholRecordBinding>(R.layout.fragment_show_alcohol_record) {

    private val homeViewModel: HomeViewModel by activityViewModels()
    private val viewModel: ShowAlcoholRecordViewModel by viewModels()
    private var adapter: AlcoholSpinnerAdapter? = null

    private val args: ShowAlcoholRecordFragmentArgs by navArgs()
    private val alcohol by lazy { args.alcohol }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        binding.rvAlcoholDetail.itemAnimator = null
        binding.rvRecord.itemAnimator = null
        binding.rvAlcoholDetail.adapter = RecordAlcoholDetailNameAdapter()
        binding.rvRecord.adapter = RecordAdapter()
        setSpinner()
        initEventObserve()
        initStateObserve()

    }

    private fun initEventObserve() {
        repeatOnStarted {
            viewModel.event.collect {
            }
        }
    }

    private fun initStateObserve() {
        repeatOnStarted {
            viewModel.selectedAlcohol.collect {
                setSpannableText(it)
            }
        }
    }

    private fun setSpinner() {
        val position = homeViewModel.existRecordLiquor.value.indexOf(alcohol)
        adapter = AlcoholSpinnerAdapter(
            requireContext(),
            R.layout.item_spinner_item, homeViewModel.existRecordLiquor.value
        )
        binding.spinner.adapter = adapter

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.changeAlcohol(homeViewModel.existRecordLiquor.value[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        binding.spinner.setSelection(position)
    }

    private fun setSpannableText(alcohol: Alcohol) {
        val spannable = SpannableString("혈중 ${alcohol.displayName} 농도").apply {
            setSpan(
                ForegroundColorSpan(requireContext().getColor(alcohol.colorResource)),
                3,
                3 + alcohol.displayName.length,
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )
        }
        binding.tvAnnounce.text = spannable
    }
}