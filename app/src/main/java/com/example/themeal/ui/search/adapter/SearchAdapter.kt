package com.example.themeal.ui.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.themeal.base.BaseAdapter
import com.example.themeal.base.BaseViewHolder
import com.example.themeal.data.model.RecentSearch
import com.example.themeal.databinding.ItemRecentSearchBinding
import com.example.themeal.util.OnClickListener

class SearchAdapter :
    BaseAdapter<RecentSearch, ItemRecentSearchBinding, SearchAdapter.ViewHolder>(RecentSearch.getDiffUtil()) {

    private var listener: OnClickListener<RecentSearch>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemRecentSearchBinding.inflate(layoutInflater, parent, false), listener)
    }

    fun addClickListener(listener: OnClickListener<RecentSearch>) {
        this.listener = listener
    }

    class ViewHolder(
        binding: ItemRecentSearchBinding,
        private val listener: OnClickListener<RecentSearch>?
    ) : BaseViewHolder<RecentSearch, ItemRecentSearchBinding>(binding), View.OnClickListener,
        View.OnLongClickListener {

        private var data: RecentSearch? = null

        init {
            binding.root.setOnClickListener(this)
            binding.root.setOnLongClickListener(this)
            binding.imageCopy.setOnClickListener(this)
        }

        override fun onBindData(data: RecentSearch) {
            binding.textSearch.text = data.keyWord
            this.data = data
        }

        override fun onClick(view: View?) {
            data?.let {
                if (view?.id == binding.imageCopy.id) {
                    listener?.onItemClick(it)
                } else {
                    listener?.onClick(it)
                }
            }
        }

        override fun onLongClick(v: View?): Boolean {
            data?.let {
                listener?.onLongClick(it)
            }
            return true
        }
    }
}
