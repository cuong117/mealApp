package com.example.themeal.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

abstract class LoadMoreLocalVM<S> : BaseViewModel() {

    private val totalList = MutableLiveData<List<S>>()

    private val _currentList = MutableLiveData<List<S>>(mutableListOf())
    val currentList: LiveData<List<S>> get() = _currentList

    private var currentIndex = 0
    protected open val itemPerPage: Int = 0

    var isLoadMore = true

    protected abstract fun getAllItem()

    protected fun submitList(list: List<S>) {
        totalList.value = list
        getNextItem()
    }

    fun getNextItem() {
        if (totalList.value == null) {
            getAllItem()
        } else{
            val allItem = totalList.value as List<S>
            val currentListItem = _currentList.value as MutableList<S>
            if (isLoadMore && currentIndex < allItem.size) {
                val endIndex =
                    if (currentIndex + itemPerPage >= allItem.size) {
                        allItem.size
                    } else {
                        currentIndex + itemPerPage
                    }
                currentListItem.addAll(allItem.slice(currentIndex until endIndex))
                currentIndex = endIndex
                _currentList.value = currentListItem
            } else {
                isLoadMore = false
            }
        }
    }
}
