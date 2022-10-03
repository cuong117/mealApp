package com.example.themeal.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.themeal.base.BaseViewModel
import com.example.themeal.data.model.Category
import com.example.themeal.data.model.MealCollapse
import com.example.themeal.data.repository.Repository

class HomeViewModel(private val mealRepository: Repository.MealRepository) : BaseViewModel() {

    private val _allMealList = MutableLiveData<List<MealCollapse>>()
    val allMealList: LiveData<List<MealCollapse>>
        get() = _allMealList

    private val _recentMealList = MutableLiveData<List<MealCollapse>>()
    val recentMealList: LiveData<List<MealCollapse>>
        get() = _recentMealList

    private val _categoryList = MutableLiveData<List<Category>>()
    val categoryList: LiveData<List<Category>>
        get() = _categoryList

    private val _newMealList = MutableLiveData<List<MealCollapse>>()
    val newMealList: LiveData<List<MealCollapse>>
        get() = _newMealList

    private var currentLetter = START_LETTER
    private val newItem = NEW_LETTER


    init {
        getMealByFirstLetter()
        getCategories()
        getNewMeal()
        getListMealRecent()
    }

    private fun getMealByFirstLetter() {
        launchAsync(
            request = { mealRepository.getMealByFirstLetter(currentLetter.toString()) },
            onSuccess = { mealResponse ->
                currentLetter++
                mealResponse.meals?.let { listMeal ->
                    _allMealList.value = listMeal.map { it.mapToMealCollapse() }.toMutableList()
                }
            }
        )
    }

    private fun getCategories() {
        launchAsync(
            request = { mealRepository.getCategories() },
            onSuccess = {
                _categoryList.value = it.categories
            }
        )
    }

    private fun getNewMeal() {
        launchAsync(
            request = { mealRepository.getMealByFirstLetter(newItem) },
            onSuccess = { mealResponse ->
                _newMealList.value = mealResponse.meals?.map { it.mapToMealCollapse() }
            }
        )
    }

    fun getListMealRecent() {
        launchAsync(
            request = { mealRepository.getListMealRecent() },
            onSuccess = {
                _recentMealList.value = it
            }
        )
    }

    companion object {
        private const val NEW_LETTER = "e"
        private const val START_LETTER = 'a'
    }
}
