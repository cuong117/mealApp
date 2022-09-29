package com.example.themeal.data.source.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.themeal.data.model.RecentSearch

@Dao
interface RecentSearchDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecentKeyWord(recentSearch: RecentSearch)

    @Query("SELECT * FROM " + MealDatabase.RECENT_SEARCH_TABLE)
    fun getAllRecentKeyWord(): List<RecentSearch>

    @Delete
    fun deleteRecentKeyWord(recentSearch: RecentSearch)
}
