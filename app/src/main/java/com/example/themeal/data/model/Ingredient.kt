package com.example.themeal.data.model

import androidx.recyclerview.widget.DiffUtil
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
        private const val urlImage = "https://www.themealdb.com/images/ingredients/"

        fun getDiffUtil() = object : DiffUtil.ItemCallback<Ingredient>() {
            override fun areItemsTheSame(oldItem: Ingredient, newItem: Ingredient): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Ingredient, newItem: Ingredient): Boolean {
                return oldItem == newItem
            }

        }
    }
}
