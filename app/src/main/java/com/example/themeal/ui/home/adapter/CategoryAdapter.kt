package com.example.themeal.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.themeal.base.BaseAdapter
import com.example.themeal.base.BaseViewHolder
import com.example.themeal.data.model.Category
import com.example.themeal.databinding.ItemCategoryBinding
import com.example.themeal.util.loadImage

class CategoryAdapter :
    BaseAdapter<Category, ItemCategoryBinding, CategoryAdapter.ViewHolder>(Category.getDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCategoryBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    class ViewHolder(binding: ItemCategoryBinding) :
        BaseViewHolder<Category, ItemCategoryBinding>(binding) {

        override fun onBindData(data: Category) {
            binding.textCategoryTitle.text = data.name
            binding.imageCategory.loadImage(binding.root.context, data.thumbnailLink)
        }
    }
}
