package com.example.themeal.ui.listmeal.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.themeal.base.BaseAdapter
import com.example.themeal.base.BaseViewHolder
import com.example.themeal.data.model.MealCollapse
import com.example.themeal.databinding.ItemCircleCornerBinding
import com.example.themeal.util.loadImage

class ListMealAdapter :
    BaseAdapter<MealCollapse, ItemCircleCornerBinding, ListMealAdapter.ViewHolder>(
        MealCollapse.getDiffUtil()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemCircleCornerBinding.inflate(layoutInflater, parent, false))
    }

    class ViewHolder(binding: ItemCircleCornerBinding) :
        BaseViewHolder<MealCollapse, ItemCircleCornerBinding>(binding) {

        override fun onBindData(data: MealCollapse) {
            binding.image.loadImage(binding.root.context, data.getLinkPreview())
            binding.textName.text = data.name
        }
    }
}
