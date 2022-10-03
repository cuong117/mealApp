package com.example.themeal.util

import com.example.themeal.data.model.MealCollapse

interface FilterInterface {

    fun filter(
        originalList: List<MealCollapse>,
        area: String?,
        ingredient: String?,
        category: String?
    ): List<MealCollapse> {
        return originalList.filter { meal ->
            var isChoose = true
            area?.let { isChoose = meal.area == it }
            ingredient?.let { isChoose = meal.ingredient?.contains(it) ?: false && isChoose  }
            category?.let { isChoose = meal.category == it && isChoose }
            isChoose
        }
    }
}
