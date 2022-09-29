package com.example.themeal.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.themeal.base.LoadMoreLocalVM
import com.example.themeal.data.model.Area
import com.example.themeal.data.model.MealCollapse
import com.example.themeal.data.repository.Repository
import com.example.themeal.util.FilterInterface

class SearchResultViewModel(
    private val repository: Repository.MealRepository
) : LoadMoreLocalVM<MealCollapse>(), FilterInterface {

    private val listArea = mutableListOf<Area?>()

    private val _backupList = MutableLiveData<List<MealCollapse>>()
    private val _myListArea = MutableLiveData<List<Area>>()
    val myListArea: LiveData<List<Area>> get() = _myListArea

    override val itemPerPage: Int
        get() = COUNT_ITEM

    fun searchMeal(query: String) {
        launchAsync(
            request = { repository.getMealByName(query) },
            onSuccess = {
                it.meals?.map { item -> item.mapToMealCollapse() }?.let { list ->
                    submitList(list)
                    _backupList.value = list
                }
            }
        )
    }

    fun getArea() {
        launchAsync(
            request = { repository.getArea() },
            onSuccess = {
                it.meals?.let { areas ->
                    listArea.clear()
                    listArea.addAll(areas)
                }
                _myListArea.value = it.meals
            }
        )
    }

    fun mealFilter(area: String?, ingredient: String?, category: String?) {
        _backupList.value?.let {
            submitList(filter(it, area, ingredient, category))
        }
    }

    companion object {
        private const val COUNT_ITEM = 6
    }
}
