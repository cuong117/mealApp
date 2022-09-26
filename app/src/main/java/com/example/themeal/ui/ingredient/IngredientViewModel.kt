package com.example.themeal.ui.ingredient

import com.example.themeal.base.LoadMoreLocalVM
import com.example.themeal.data.model.Ingredient
import com.example.themeal.data.repository.Repository

class IngredientViewModel(
    private val mealRepository: Repository.MealRepository
) : LoadMoreLocalVM<Ingredient>() {

    override val itemPerPage: Int
        get() = COUNT_ITEM

    override fun getAllItem() {
        launchAsync(
            request = { mealRepository.getListIngredient() },
            onSuccess = {
                it.meals?.let { list ->
                    submitList(list)
                }
            }
        )
    }

    companion object {
        private const val COUNT_ITEM = 15
    }
}
