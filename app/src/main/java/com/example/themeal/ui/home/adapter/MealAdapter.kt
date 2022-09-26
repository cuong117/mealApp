package com.example.themeal.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.themeal.base.BaseAdapter
import com.example.themeal.base.BaseViewHolder
import com.example.themeal.data.model.MealCollapse
import com.example.themeal.databinding.ItemMealBinding
import com.example.themeal.util.loadImage

class MealAdapter :
    BaseAdapter<MealCollapse, ItemMealBinding, MealAdapter.ViewHolder>(MealCollapse.getDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemMealBinding.inflate(layoutInflater, parent, false))
    }

    override fun submitList(list: List<MealCollapse>?) {
        super.submitList(list)
    }

    class ViewHolder(binding: ItemMealBinding) :
        BaseViewHolder<MealCollapse, ItemMealBinding>(binding) {

        override fun onBindData(data: MealCollapse) {
            binding.textTitleMeal.text = data.name
            binding.imageMeal.loadImage(binding.root.context, data.getLinkPreview())
        }
    }
}
