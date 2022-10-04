package com.example.themeal.ui.ingredient.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.themeal.base.BaseAdapter
import com.example.themeal.base.BaseViewHolder
import com.example.themeal.data.model.Ingredient
import com.example.themeal.databinding.ItemCircleCornerBinding
import com.example.themeal.util.OnClickListener
import com.example.themeal.util.loadImage

class IngredientAdapter :
    BaseAdapter<Ingredient, ItemCircleCornerBinding, IngredientAdapter.ViewHolder>(Ingredient.getDiffUtil()) {

    private var listener: OnClickListener<Ingredient>? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemCircleCornerBinding.inflate(layoutInflater, parent, false), listener)
    }

    fun updateListener(listener: OnClickListener<Ingredient>) {
        this.listener = listener
    }

    class ViewHolder(
        binding: ItemCircleCornerBinding,
        private val listener: OnClickListener<Ingredient>?
    ) : BaseViewHolder<Ingredient, ItemCircleCornerBinding>(binding), View.OnClickListener {

        private var data: Ingredient? = null

        init {
            binding.root.setOnClickListener(this)
        }

        override fun onBindData(data: Ingredient) {
            this.data = data
            binding.image.loadImage(binding.root.context, data.getThumbnail())
            binding.textName.text = data.name
        }

        override fun onClick(v: View?) {
            data?.let {
                listener?.onClick(it)
            }
        }
    }
}
