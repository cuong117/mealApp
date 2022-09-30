package com.example.themeal.ui.listmeal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.themeal.base.LoadMoreLocalVM
import com.example.themeal.data.model.MealCollapse
import com.example.themeal.data.repository.Repository

class ListMealViewModel(
    private val mealRepository: Repository.MealRepository
) : LoadMoreLocalVM<MealCollapse>() {

    private val _listAllMeal = MutableLiveData<List<MealCollapse>>(mutableListOf())
    val listAllMeal: LiveData<List<MealCollapse>> get() = _listAllMeal

    var isAllMealLoadMore = true
    private var currentLetter = START_LETTER

    override val itemPerPage: Int = ITEM_COUNT

    fun getMealByCategory(name: String) {
        launchAsync(
            request = { mealRepository.getMealByCategory(name) },
            onSuccess = {
                it.meals?.let { list ->
                    submitList(list.map { meal -> meal.mapToMealCollapse() })
                }
            }
        )
    }

    fun getAllMeal() {
        if (currentLetter <= END_LETTER) {
            launchAsync(
                request = {
                    mealRepository.getMealByFirstLetter(currentLetter.toString())
                },
                onSuccess = {
                    it.meals?.let { meals ->
                        val list = _listAllMeal.value as MutableList<MealCollapse>
                        list.addAll(meals.map { meal -> meal.mapToMealCollapse() })
                        _listAllMeal.value = list
                    }
                    currentLetter++
                    if (it.meals == null) {
                        getAllMeal()
                    }
                }
            )
        } else {
            isAllMealLoadMore = false
        }
    }

    companion object {
        private const val ITEM_COUNT = 15
        private const val START_LETTER = 'a'
        private const val END_LETTER = 'z'
    }
}
