package com.mashow.presentation.ui.main.record.food

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mashow.presentation.util.getTodayDateWithDay
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class FoodRecordDetailEvent {
    data object NavigateBack : FoodRecordDetailEvent()
    data object AddEditFood : FoodRecordDetailEvent()
    data class CompleteEditFood(val list: List<String>) : FoodRecordDetailEvent()
}

data class FoodDetail(
    var id: Int,
    var name: String
)


@HiltViewModel
class FoodRecordDetailViewModel @Inject constructor() : ViewModel() {

    private val _event = MutableSharedFlow<FoodRecordDetailEvent>()
    val event: SharedFlow<FoodRecordDetailEvent> = _event.asSharedFlow()

    private val foodList = mutableListOf<FoodDetail>()

    val date = getTodayDateWithDay()

    fun navigateBack() {
        viewModelScope.launch {
            _event.emit(FoodRecordDetailEvent.NavigateBack)
        }
    }

    fun addEditFood() {
        viewModelScope.launch {
            _event.emit(FoodRecordDetailEvent.AddEditFood)
        }
    }

    fun createFoodItem(id: Int) {
        foodList.add(FoodDetail(id, ""))
    }

    fun deleteFoodItem(id: Int) {
        foodList.filter {
            it.id == id
        }
    }

    fun addFoodList(food: String, id: Int) {
        foodList.forEach {
            if (it.id == id) it.name = food
        }
    }

    fun completeEditFood() {
        val list = foodList.filter {
            it.name.isNotBlank()
        }
        viewModelScope.launch {
            _event.emit(FoodRecordDetailEvent.CompleteEditFood(list.map { it.name }))
        }
    }

}