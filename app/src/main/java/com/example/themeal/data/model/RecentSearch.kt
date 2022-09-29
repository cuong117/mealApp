package com.example.themeal.data.model

import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.themeal.data.source.database.MealDatabase

@Entity(
    tableName = MealDatabase.RECENT_SEARCH_TABLE,
    indices = [Index(value = ["keyWord"], unique = true)]
)
data class RecentSearch(
    val keyWord: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    companion object {

        fun getDiffUtil() = object : DiffUtil.ItemCallback<RecentSearch>() {
            override fun areItemsTheSame(oldItem: RecentSearch, newItem: RecentSearch): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: RecentSearch, newItem: RecentSearch): Boolean {
                return oldItem == newItem
            }
        }
    }
}
