package com.example.themeal.ui.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.example.themeal.base.BaseAdapter
import com.example.themeal.base.BaseViewHolder
import com.example.themeal.data.model.MealCollapse
import com.example.themeal.databinding.ItemLoadingBinding
import com.example.themeal.databinding.ItemSearchResultBinding
import com.example.themeal.util.loadImage

class SearchResultAdapter :
    BaseAdapter<MealCollapse, ViewBinding, BaseViewHolder<MealCollapse, ViewBinding>>(
        MealCollapse.getDiffUtil()
    ) {

    private var isLoadMore = false

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<MealCollapse, ViewBinding> {
        val layoutInflater = LayoutInflater.from(parent.context)
        if (viewType == LOAD_MORE) {
            return LoadingViewHolder(ItemLoadingBinding.inflate(layoutInflater, parent, false))
        }
        return ViewHolder(ItemSearchResultBinding.inflate(layoutInflater, parent, false))
    }

    fun submitList(list: List<MealCollapse>, isLoadMore: Boolean) {
        this.isLoadMore = isLoadMore
        submitList(list)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return if (isLoadMore) currentList.size + 1 else currentList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == currentList.size) LOAD_MORE else ITEM_DATA
    }

    class ViewHolder(
        override val binding: ItemSearchResultBinding
    ) : BaseViewHolder<MealCollapse, ViewBinding>(binding) {

        override fun onBindData(data: MealCollapse) {
            binding.imageResult.loadImage(binding.root.context, data.getLinkPreview())
            if (data.tags == null) {
                binding.tagResult.visibility = View.GONE
            } else {
                binding.tagResult.text = data.tags
            }
            binding.textResult.text = data.name
        }
    }

    class LoadingViewHolder(binding: ItemLoadingBinding) :
        BaseViewHolder<MealCollapse, ViewBinding>(binding) {

        override fun onBindData(data: MealCollapse) {
            // No-ip
        }

    }

    companion object {
        private const val LOAD_MORE = 0
        private const val ITEM_DATA = 1
    }
}
