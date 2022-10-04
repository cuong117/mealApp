package com.example.themeal.ui.search.searchIngredient

import com.example.themeal.base.LoadMoreLocalVM
import com.example.themeal.data.model.Ingredient

class IngredientResultViewModel: LoadMoreLocalVM<Ingredient>() {

    override val itemPerPage: Int
        get() = ITEM_COUNT

    fun addList(list: List<Ingredient>) {
        submitList(list)
    }

    companion object {
        private const val ITEM_COUNT = 7
    }
}
