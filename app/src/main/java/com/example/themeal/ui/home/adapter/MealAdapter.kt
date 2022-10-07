package com.example.themeal.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.themeal.base.BaseAdapter
import com.example.themeal.base.BaseViewHolder
import com.example.themeal.data.model.MealCollapse
import com.example.themeal.databinding.ItemMealBinding
import com.example.themeal.util.OnClickListener
import com.example.themeal.util.loadImage

class MealAdapter :
    BaseAdapter<MealCollapse, ItemMealBinding, MealAdapter.ViewHolder>(MealCollapse.getDiffUtil()) {

    private var listener: OnClickListener<Any>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemMealBinding.inflate(layoutInflater, parent, false))
    }

    override fun submitList(list: List<MealCollapse>?) {
        super.submitList(list)
    }

    fun updateListener(listener: OnClickListener<Any>?) {
        this.listener = listener
    }

    inner class ViewHolder(binding: ItemMealBinding) :
        BaseViewHolder<MealCollapse, ItemMealBinding>(binding), View.OnClickListener {

        private var data: MealCollapse? = null

        init {
            binding.root.setOnClickListener(this)
        }

        override fun onBindData(data: MealCollapse) {
            this.data = data
            binding.textTitleMeal.text = data.name
            binding.imageMeal.loadImage(binding.root.context, data.getLinkPreview())
        }

        override fun onClick(v: View?) {
            data?.let {
                listener?.onClick(it)
            }
        }
    }
}
