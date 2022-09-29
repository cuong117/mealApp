package com.example.themeal.ui.ingredient.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.themeal.base.BaseAdapter
import com.example.themeal.base.BaseViewHolder
import com.example.themeal.data.model.Ingredient
import com.example.themeal.databinding.ItemIngredientBinding
import com.example.themeal.util.loadImage

class IngredientAdapter :
    BaseAdapter<Ingredient, ItemIngredientBinding, IngredientAdapter.ViewHolder>(Ingredient.getDiffUtil()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemIngredientBinding.inflate(layoutInflater, parent, false))
    }

    class ViewHolder(binding: ItemIngredientBinding) :
        BaseViewHolder<Ingredient, ItemIngredientBinding>(binding) {

        override fun onBindData(data: Ingredient) {
            binding.imageMeal.loadImage(binding.root.context, data.getThumbnail())
            binding.textTitleIngredient.text = data.name
        }
    }
}
