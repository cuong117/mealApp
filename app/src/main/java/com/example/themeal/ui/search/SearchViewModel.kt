package com.example.themeal.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.themeal.base.BaseViewModel
import com.example.themeal.data.model.RecentSearch
import com.example.themeal.data.repository.Repository

class SearchViewModel(
    private val searchRepository: Repository.RecentSearchRepository
) : BaseViewModel() {

    private val _listKeyWord = MutableLiveData<List<RecentSearch>>()

    private val _currentList = MutableLiveData<List<RecentSearch>>()
    val currentList: LiveData<List<RecentSearch>> get() = _currentList

    private var isInit = true

    fun getAllKeyWord() {
        launchAsync(
            request = { searchRepository.getAllKeyWord() },
            onSuccess = {
                _listKeyWord.value = it.reversed()
                if (isInit) {
                    _currentList.value = it.reversed()
                    isInit = false
                }
            }
        )
    }

    fun addAllKeyWord() {
        _currentList.value = _listKeyWord.value
    }

    fun insertKeyWord(recentSearch: RecentSearch) {
        launchAsync(
            request = { searchRepository.addNewKeyWord(recentSearch) },
            onSuccess = { getAllKeyWord() }
        )
    }

    fun deleteKeyWord(recentSearch: RecentSearch) {
        launchAsync(
            request = { searchRepository.deleteKeyWord(recentSearch) },
            onSuccess = {
                val myList = _currentList.value as? MutableList<RecentSearch>
                myList?.remove(recentSearch)
                _currentList.value = myList
                getAllKeyWord()
            }
        )
    }

    fun filterKeyWord(text: String) {
        val list = _listKeyWord.value as? MutableList<RecentSearch>
        val updateList = mutableListOf<RecentSearch>()
        list?.apply { updateList.addAll(filter { it.keyWord.contains(text) }) }
        _currentList.value = updateList
    }


}
