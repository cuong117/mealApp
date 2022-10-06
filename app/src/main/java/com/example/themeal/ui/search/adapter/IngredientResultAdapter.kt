package com.example.themeal.ui.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.example.themeal.base.BaseAdapter
import com.example.themeal.base.BaseViewHolder
import com.example.themeal.constant.Constant
import com.example.themeal.data.model.Ingredient
import com.example.themeal.databinding.ItemIngredientResultBinding
import com.example.themeal.databinding.ItemLoadingBinding
import com.example.themeal.util.OnClickListener
import com.example.themeal.util.loadImage

class IngredientResultAdapter :
    BaseAdapter<Ingredient, ViewBinding, BaseViewHolder<Ingredient, ViewBinding>>(
        Ingredient.getDiffUtil()
    ) {

    private var listener: OnClickListener<Ingredient>? = null
    private var isLoadMore = false

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<Ingredient, ViewBinding> {
        val layoutInflater = LayoutInflater.from(parent.context)
        if (viewType == Constant.ITEM_DATA) {
            return ViewHolder(ItemIngredientResultBinding.inflate(layoutInflater, parent, false))
        }
        return LoadingViewHolder(ItemLoadingBinding.inflate(layoutInflater, parent, false))
    }

    fun submitList(list: List<Ingredient>, isLoadMore: Boolean) {
        this.isLoadMore = isLoadMore
        submitList(list)
        notifyDataSetChanged()
    }

    fun updateListener(listener: OnClickListener<Ingredient>) {
        this.listener = listener
    }

    override fun getItemCount(): Int {
        return if (isLoadMore) currentList.size + 1 else currentList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == currentList.size) Constant.LOAD_MORE else Constant.ITEM_DATA
    }

    inner class ViewHolder(
        override val binding: ItemIngredientResultBinding
    ) :
        BaseViewHolder<Ingredient, ViewBinding>(binding), View.OnClickListener {

        private var data: Ingredient? = null

        init {
            binding.root.setOnClickListener(this)
        }

        override fun onBindData(data: Ingredient) {
            this.data = data
            binding.imageResult.loadImage(binding.root.context, data.getThumbnail())
            binding.textName.text = data.name
        }

        override fun onClick(v: View?) {
            data?.let {
                listener?.onClick(it)
            }
        }
    }

    class LoadingViewHolder(binding: ItemLoadingBinding) :
        BaseViewHolder<Ingredient, ViewBinding>(binding) {

        override fun onBindData(data: Ingredient) {
            // No-ip
        }
    }
}
