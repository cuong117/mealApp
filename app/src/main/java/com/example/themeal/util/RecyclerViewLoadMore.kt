package com.example.themeal.util

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

interface RecyclerViewLoadMore {

    fun loadMore()

    fun isLoadMore(): Boolean

    fun startLoadMore(recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as? GridLayoutManager
                val size = recyclerView.adapter?.itemCount ?: 0
                layoutManager?.apply {
                    if (isLoadMore() && this.findLastCompletelyVisibleItemPosition() == size - 1) {
                        loadMore()
                    }
                }
            }
        })
    }
}
