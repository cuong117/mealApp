package com.example.themeal.ui.mealdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.themeal.base.BaseViewModel
import com.example.themeal.data.model.MealCollapse
import com.example.themeal.data.repository.Repository

class MealDetailViewModel(private val mealRepository: Repository.MealRepository) : BaseViewModel() {

    private val _meal = MutableLiveData<List<MealCollapse>>()
    val meal: LiveData<List<MealCollapse>> get() = _meal

    fun getMeal(id: String) {
        launchAsync(
            request = { mealRepository.getMealById(id) },
            onSuccess = {
                _meal.value = it.meals?.map { mealDetail -> mealDetail.mapToMealCollapse() }
            }
        )
    }

    fun insertRecentMeal(mealCollapse: MealCollapse) {
        launchAsync(
            request = { mealRepository.insertMealRecent(mealCollapse) },
            onSuccess = {}
        )
    }
}
