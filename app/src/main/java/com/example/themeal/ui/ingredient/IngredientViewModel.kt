package com.example.themeal.ui.ingredient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.themeal.base.LoadMoreLocalVM
import com.example.themeal.data.model.Ingredient
import com.example.themeal.data.repository.Repository

class IngredientViewModel(
    private val mealRepository: Repository.MealRepository
) : LoadMoreLocalVM<Ingredient>() {

    private val _listIngredient = MutableLiveData<List<Ingredient>>()
    val listIngredient: LiveData<List<Ingredient>> get() = _listIngredient

    private val _listIngredientResult = MutableLiveData<List<Ingredient>>()
    val listIngredientResult: LiveData<List<Ingredient>> get() = _listIngredientResult

    override val itemPerPage: Int
        get() = COUNT_ITEM

    init {
        getAllItem()
    }

    fun getAllItem() {
        launchAsync(
            request = { mealRepository.getListIngredient() },
            onSuccess = {
                it.meals?.let { list ->
                    _listIngredient.value = list
                    submitList(list)
                }
            }
        )
    }

    fun findIngredient(name: String): Ingredient? {
        val list = _listIngredient.value as? MutableList<Ingredient>
        return list?.find { it.name == name }
    }

    fun searchIngredient(text: String) {
        val list = _listIngredient.value as? MutableList<Ingredient>
        val resultList = list?.filter { it.name?.contains(text) == true }
        resultList?.let {
            _listIngredientResult.value = it
        }
    }

    companion object {
        private const val COUNT_ITEM = 15
    }
}
