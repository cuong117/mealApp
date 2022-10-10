package com.example.themeal.ui.favorite.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.themeal.base.BaseAdapter
import com.example.themeal.base.BaseViewHolder
import com.example.themeal.data.model.MealCollapse
import com.example.themeal.databinding.ItemCircleCornerBinding
import com.example.themeal.util.OnClickListener
import com.example.themeal.util.loadImage

class FavoriteAdapter :
    BaseAdapter<MealCollapse, ItemCircleCornerBinding, FavoriteAdapter.ViewHolder>(MealCollapse.getDiffUtil()) {

    private var listener: OnClickListener<MealCollapse>? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemCircleCornerBinding.inflate(layoutInflater, parent, false))
    }

    fun updateListener(listener: OnClickListener<MealCollapse>) {
        this.listener = listener
    }

    inner class ViewHolder(
        binding: ItemCircleCornerBinding
    ) : BaseViewHolder<MealCollapse, ItemCircleCornerBinding>(binding), View.OnClickListener {

        private var data: MealCollapse? = null

        init {
            binding.root.setOnClickListener(this)
        }

        override fun onBindData(data: MealCollapse) {
            this.data = data
            binding.image.loadImage(binding.root.context, data.getLinkPreview())
            binding.textName.text = data.name
        }

        override fun onClick(v: View?) {
            data?.let {
                listener?.onClick(it)
            }
        }
    }
}
