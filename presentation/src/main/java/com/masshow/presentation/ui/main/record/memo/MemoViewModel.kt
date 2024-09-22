package com.masshow.presentation.ui.main.record.memo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masshow.data.model.BaseState
import com.masshow.data.model.request.LiquorItem
import com.masshow.data.model.request.MemoItem
import com.masshow.data.model.request.RecordRequest
import com.masshow.data.model.request.SideDishItem
import com.masshow.data.repository.MainRepository
import com.masshow.presentation.ui.main.record.RecordFormData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class MemoEvents{
    data object FinishRecord: MemoEvents()
}

@HiltViewModel
class MemoViewModel @Inject constructor(
    private val repository: MainRepository
): ViewModel() {

    private val _events = MutableSharedFlow<MemoEvents>()
    val events: SharedFlow<MemoEvents> = _events.asSharedFlow()

    val memo = MutableStateFlow("")

    fun record(){
        viewModelScope.launch {
            repository.record(
                RecordRequest(
                    liquors = RecordFormData.selectedAlcoholMap.map{
                        LiquorItem(
                            it.first,
                            it.second
                        )
                    },
                    memos = listOf(MemoItem(description = RecordFormData.memo)),
                    rating = RecordFormData.rating,
                    sideDishes = RecordFormData.sideDishes.map{
                        SideDishItem(it)
                    }
                )
            ).let{
                when(it){
                    is BaseState.Success -> {
                        _events.emit(MemoEvents.FinishRecord)
                    }

                    is BaseState.Error -> {

                    }
                }
            }
        }
    }
}