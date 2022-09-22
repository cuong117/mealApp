package com.example.themeal.data.model

import com.google.gson.annotations.SerializedName

data class Ingredient(
    @SerializedName("idIngredient")
    val id: String,
    @SerializedName("strIngredient")
    val name: String,
    @SerializedName("strDescription")
    val description: String
) {
    fun getSmallThumbnail() = "$urlImage$name-Small.png"

    fun getThumbnail() = "$urlImage$name.png"

    companion object {
        private const val urlImage = "www.themealdb.com/images/ingredients/"
    }
}
