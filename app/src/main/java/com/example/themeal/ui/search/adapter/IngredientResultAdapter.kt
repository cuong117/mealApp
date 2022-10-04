package com.example.themeal.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.themeal.base.BaseAdapter
import com.example.themeal.base.BaseViewHolder
import com.example.themeal.data.model.Ingredient
import com.example.themeal.databinding.ItemIngredientResultBinding
import com.example.themeal.util.loadImage

class IngredientResultAdapter :
    BaseAdapter<Ingredient, ItemIngredientResultBinding, IngredientResultAdapter.ViewHolder>(
        Ingredient.getDiffUtil()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemIngredientResultBinding.inflate(layoutInflater, parent, false))
    }

    class ViewHolder(binding: ItemIngredientResultBinding) :
        BaseViewHolder<Ingredient, ItemIngredientResultBinding>(binding) {

        override fun onBindData(data: Ingredient) {
            binding.imageResult.loadImage(binding.root.context, data.getThumbnail())
            binding.textName.text = data.name
        }
    }
}
