package com.masshow.presentation.ui.main.show

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.masshow.presentation.R
import com.masshow.presentation.base.BaseFragment
import com.masshow.presentation.databinding.FragmentShowAlcoholRecordBinding
import com.masshow.presentation.ui.main.show.adapter.AlcoholSpinnerAdapter

class ShowAlcoholRecordFragment :
    BaseFragment<FragmentShowAlcoholRecordBinding>(R.layout.fragment_show_alcohol_record) {

    private val viewModel: ShowAlcoholRecordViewModel by viewModels()
    private var adapter: AlcoholSpinnerAdapter? = null

    private val args: ShowAlcoholRecordFragmentArgs by navArgs()
    val alcohol by lazy { args.alcohol }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initEventObserve()
        viewModel.getExistRecord()
    }

    private fun initEventObserve() {
        repeatOnStarted {
            viewModel.event.collect {
                when (it) {
                    is ShowAlcoholRecordEvent.FinishPatchExistRecord -> setSpinner()
                }
            }
        }
    }

    private fun setSpinner() {
        adapter = AlcoholSpinnerAdapter(
            requireContext(),
            R.layout.item_spinner_item, viewModel.existRecordLiquor.value
        )
        binding.spinner.adapter = adapter

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.changeAlcohol(viewModel.existRecordLiquor.value[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }
}