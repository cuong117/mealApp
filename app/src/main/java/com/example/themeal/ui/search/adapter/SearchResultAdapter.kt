package com.example.themeal.ui.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.example.themeal.R
import com.example.themeal.base.BaseAdapter
import com.example.themeal.base.BaseViewHolder
import com.example.themeal.constant.Constant
import com.example.themeal.data.model.MealCollapse
import com.example.themeal.databinding.ItemLoadingBinding
import com.example.themeal.databinding.ItemSearchResultBinding
import com.example.themeal.util.OnClickListener
import com.example.themeal.util.loadImage

class SearchResultAdapter :
    BaseAdapter<MealCollapse, ViewBinding, BaseViewHolder<MealCollapse, ViewBinding>>(
        MealCollapse.getDiffUtil()
    ) {

    private var listener: OnClickListener<MealCollapse>? = null
    private var listFavoriteId = mutableListOf<String>()
    private var isLoadMore = false

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<MealCollapse, ViewBinding> {
        val layoutInflater = LayoutInflater.from(parent.context)
        if (viewType == Constant.LOAD_MORE) {
            return LoadingViewHolder(ItemLoadingBinding.inflate(layoutInflater, parent, false))
        }
        return ViewHolder(ItemSearchResultBinding.inflate(layoutInflater, parent, false))
    }

    fun updateListener(listener: OnClickListener<MealCollapse>) {
        this.listener = listener
    }

    fun submitList(list: List<MealCollapse>, isLoadMore: Boolean) {
        this.isLoadMore = isLoadMore
        submitList(list)
        notifyDataSetChanged()
    }

    fun updateListFavorite(listFavoriteId: List<String>) {
        this.listFavoriteId.clear()
        this.listFavoriteId.addAll(listFavoriteId)
    }

    override fun getItemCount(): Int {
        return if (isLoadMore) currentList.size + 1 else currentList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == currentList.size) Constant.LOAD_MORE else Constant.ITEM_DATA
    }

    inner class ViewHolder(
        override val binding: ItemSearchResultBinding
    ) : BaseViewHolder<MealCollapse, ViewBinding>(binding), View.OnClickListener {

        private var data: MealCollapse? = null

        init {
            binding.root.setOnClickListener(this)
            binding.imageFavorite.setOnClickListener(this)
        }

        override fun onBindData(data: MealCollapse) {
            this.data = data
            if (data.id in listFavoriteId) {
                binding.imageFavorite.setImageResource(R.drawable.ic_favorite_solid)
            } else {
                binding.imageFavorite.setImageResource(R.drawable.ic_favorite)
            }
            binding.imageResult.loadImage(binding.root.context, data.getLinkPreview())
            if (data.tags == null) {
                binding.tagResult.visibility = View.GONE
            } else {
                binding.tagResult.text = data.tags
            }
            binding.textResult.text = data.name
        }

        override fun onClick(view: View?) {
            data?.let {
                when (view?.id) {
                    binding.imageFavorite.id -> {
                        listener?.onItemClick(it)
                    }
                    else -> listener?.onClick(it)
                }
            }
        }
    }

    class LoadingViewHolder(binding: ItemLoadingBinding) :
        BaseViewHolder<MealCollapse, ViewBinding>(binding) {

        override fun onBindData(data: MealCollapse) {
            // No-ip
        }
    }
}
