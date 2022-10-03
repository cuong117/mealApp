package com.example.themeal.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.themeal.base.BaseAdapter
import com.example.themeal.base.BaseViewHolder
import com.example.themeal.data.model.Category
import com.example.themeal.databinding.ItemCategoryBinding
import com.example.themeal.util.OnClickListener
import com.example.themeal.util.loadImage

class CategoryAdapter :
    BaseAdapter<Category, ItemCategoryBinding, CategoryAdapter.ViewHolder>(Category.getDiffUtil()) {

    private var listener: OnClickListener<Any>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCategoryBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    fun updateListener(listener: OnClickListener<Any>?) {
        this.listener = listener
    }

    inner class ViewHolder(
        binding: ItemCategoryBinding
    ) : BaseViewHolder<Category, ItemCategoryBinding>(binding), View.OnClickListener {

        private var data: Category? = null

        init {
            binding.root.setOnClickListener(this)
        }

        override fun onBindData(data: Category) {
            binding.textCategoryTitle.text = data.name
            binding.imageCategory.loadImage(binding.root.context, data.thumbnailLink)
            this.data = data
        }

        override fun onClick(v: View?) {
            data?.let {
                listener?.onClick(it)
            }
        }
    }
}
