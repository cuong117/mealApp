package com.example.themeal.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

abstract class LoadMoreLocalVM<S> : BaseViewModel() {

    private val totalList = mutableListOf<S>()

    private val _currentList = MutableLiveData<List<S>>()
    val currentList: LiveData<List<S>> get() = _currentList

    private var currentIndex = 0
    protected abstract val itemPerPage: Int

    var isLoadMore = true

    protected fun submitList(list: List<S>) {
        totalList.clear()
        totalList.addAll(list)
        currentIndex = 0
        isLoadMore = list.isNotEmpty()
        _currentList.value = mutableListOf()
        getNextItem()
    }

    fun getNextItem() {
        if (totalList.isNotEmpty()) {
            var currentListItem = _currentList.value as? MutableList<S>
            if (currentListItem.isNullOrEmpty()) {
                currentListItem = mutableListOf()
            }
            if (isLoadMore && currentIndex < totalList.size) {
                val endIndex =
                    if (currentIndex + itemPerPage >= totalList.size) {
                        isLoadMore = false
                        totalList.size
                    } else {
                        currentIndex + itemPerPage
                    }
                currentListItem.addAll(totalList.slice(currentIndex until endIndex))
                currentIndex = endIndex
                _currentList.value = currentListItem
            }
        }
    }
}
