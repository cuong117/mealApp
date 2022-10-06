package com.example.themeal.ui.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.themeal.R
import com.example.themeal.base.BaseAdapter
import com.example.themeal.base.BaseViewHolder
import com.example.themeal.data.model.MealCollapse
import com.example.themeal.databinding.ItemSearchResultBinding
import com.example.themeal.util.OnClickListener
import com.example.themeal.util.loadImage

class SearchFavoriteAdapter :
    BaseAdapter<MealCollapse, ItemSearchResultBinding, SearchFavoriteAdapter.ViewHolder>(
        MealCollapse.getDiffUtil()
    ) {

    private var listener: OnClickListener<MealCollapse>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemSearchResultBinding.inflate(layoutInflater, parent, false))
    }

    fun updateListener(listener: OnClickListener<MealCollapse>) {
        this.listener = listener
    }

    inner class ViewHolder(binding: ItemSearchResultBinding) :
        BaseViewHolder<MealCollapse, ItemSearchResultBinding>(binding), View.OnClickListener {

        private var data: MealCollapse? = null

        init {
            binding.root.setOnClickListener(this)
            binding.imageFavorite.setOnClickListener(this)
        }

        override fun onBindData(data: MealCollapse) {
            this.data = data
            binding.imageFavorite.setImageResource(R.drawable.ic_favorite_solid)
            binding.imageResult.loadImage(binding.root.context, data.getLinkPreview())
            binding.textResult.text = data.name
            binding.tagResult.text = data.tags
        }

        override fun onClick(view: View?) {
            data?.let {
                if (view?.id == binding.imageFavorite.id) {
                    listener?.onItemClick(it)
                } else {
                    listener?.onClick(it)
                }
            }
        }
    }
}
