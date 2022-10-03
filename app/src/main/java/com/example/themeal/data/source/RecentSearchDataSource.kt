package com.example.themeal.data.source

import com.example.themeal.data.model.RecentSearch
import com.example.themeal.data.source.database.RecentSearchDAO

class RecentSearchDataSource(
    private val recentSearchDAO: RecentSearchDAO
) : DataSource.RecentSearchDataSource {

    override suspend fun getAllKeyWord(): List<RecentSearch> {
        return recentSearchDAO.getAllRecentKeyWord()
    }

    override suspend fun addNewKeyWord(recentSearch: RecentSearch) {
        recentSearchDAO.insertRecentKeyWord(recentSearch)
    }

    override suspend fun deleteKeyWord(recentSearch: RecentSearch) {
        recentSearchDAO.deleteRecentKeyWord(recentSearch)
    }
}
