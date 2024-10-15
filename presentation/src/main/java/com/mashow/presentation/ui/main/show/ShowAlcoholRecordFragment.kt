package com.mashow.presentation.ui.main.show

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mashow.presentation.R
import com.mashow.presentation.base.BaseFragment
import com.mashow.presentation.databinding.FragmentShowAlcoholRecordBinding
import com.mashow.presentation.ui.main.home.HomeViewModel
import com.mashow.presentation.ui.main.show.adapter.AlcoholSpinnerAdapter
import com.mashow.presentation.ui.main.show.adapter.RecordAdapter
import com.mashow.presentation.ui.main.show.adapter.RecordAlcoholDetailNameAdapter
import com.mashow.presentation.util.Alcohol
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
                when (it) {
                    is ShowAlcoholRecordEvent.NavigateToBack -> findNavController().navigateUp()
                    is ShowAlcoholRecordEvent.NavigateToDetail -> findNavController().toShowAlcoholRecordDetail(
                        it.id
                    )

                    is ShowAlcoholRecordEvent.NavigateToRecord -> findNavController().toRecord()
                    is ShowAlcoholRecordEvent.ShowLoading -> showLoading(requireContext())
                    is ShowAlcoholRecordEvent.DismissLoading -> dismissLoading()
                    is ShowAlcoholRecordEvent.ShowToastMessage -> showToastMessage(it.msg)
                }

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
        setRvListener()
    }

    private fun setRvListener() {
        binding.rvRecord.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisibleItemPosition =
                    (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                val itemTotalCount = recyclerView.adapter?.itemCount?.minus(1)

                if (lastVisibleItemPosition == itemTotalCount) {
                    viewModel.getMonthlyRecord()
                }
            }
        })
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

    private fun NavController.toShowAlcoholRecordDetail(id: Long) {
        val action =
            ShowAlcoholRecordFragmentDirections.actionShowAlcoholRecordFragmentToShowAlcoholRecordDetailFragment(
                id
            )
        navigate(action)
    }

    private fun NavController.toRecord() {
        val action =
            ShowAlcoholRecordFragmentDirections.actionShowAlcoholRecordFragmentToAlcoholSelectFragment()
        navigate(action)
    }
}