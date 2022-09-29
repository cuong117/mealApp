package com.example.themeal.data.repository

import com.example.themeal.base.BaseRepository
import com.example.themeal.data.model.RecentSearch
import com.example.themeal.data.source.DataSource

class RecentSearchRepository(
    private val dataSource: DataSource.RecentSearchDataSource
) : Repository.RecentSearchRepository, BaseRepository() {

    override suspend fun getAllKeyWord() = getResult { dataSource.getAllKeyWord() }

    override suspend fun addNewKeyWord(recentSearch: RecentSearch) =
        getResult { dataSource.addNewKeyWord(recentSearch) }

    override suspend fun deleteKeyWord(recentSearch: RecentSearch) =
        getResult { dataSource.deleteKeyWord(recentSearch) }
}
