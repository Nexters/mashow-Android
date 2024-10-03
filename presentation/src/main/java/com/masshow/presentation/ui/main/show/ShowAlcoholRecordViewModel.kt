package com.masshow.presentation.ui.main.show

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masshow.data.model.BaseState
import com.masshow.data.repository.MainRepository
import com.masshow.presentation.util.Alcohol
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ShowAlcoholRecordEvent {
    data object FinishPatchExistRecord : ShowAlcoholRecordEvent()
}

@HiltViewModel
class ShowAlcoholRecordViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _event = MutableSharedFlow<ShowAlcoholRecordEvent>()
    val event: SharedFlow<ShowAlcoholRecordEvent> = _event.asSharedFlow()

    var selectedAlcohol = MutableStateFlow(Alcohol.SOJU)
    val existRecordLiquor = MutableStateFlow<List<Alcohol>>(emptyList())

    fun getExistRecord() {
        viewModelScope.launch {
            repository.recordExistLiquor().let {
                when (it) {
                    is BaseState.Success -> {
                        it.data?.let { data ->
                            existRecordLiquor.update {
                                data.liquorHistoryTypes.map { item ->
                                    Alcohol.nameToEnum(item)
                                }
                            }
                        }

                        _event.emit(ShowAlcoholRecordEvent.FinishPatchExistRecord)
                    }

                    is BaseState.Error -> {

                    }
                }
            }
        }
    }

    fun changeAlcohol(alcohol: Alcohol) {
        selectedAlcohol.value = alcohol
    }

}