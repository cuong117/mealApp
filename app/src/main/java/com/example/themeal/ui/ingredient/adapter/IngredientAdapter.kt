package com.example.themeal.ui.ingredient.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.themeal.base.BaseAdapter
import com.example.themeal.base.BaseViewHolder
import com.example.themeal.data.model.Ingredient
import com.example.themeal.databinding.ItemCircleCornerBinding
import com.example.themeal.util.loadImage

class IngredientAdapter :
    BaseAdapter<Ingredient, ItemCircleCornerBinding, IngredientAdapter.ViewHolder>(Ingredient.getDiffUtil()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemCircleCornerBinding.inflate(layoutInflater, parent, false))
    }

    class ViewHolder(binding: ItemCircleCornerBinding) :
        BaseViewHolder<Ingredient, ItemCircleCornerBinding>(binding) {

        override fun onBindData(data: Ingredient) {
            binding.image.loadImage(binding.root.context, data.getThumbnail())
            binding.textName.text = data.name
        }
    }
}
