package com.example.themeal.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.themeal.data.source.database.MealDatabase

@Entity(tableName = MealDatabase.RECENT_TABLE)
data class MealCollapse(
    @PrimaryKey
    val id: String,
    val name: String?,
    val category: String?,
    val area: String?,
    val instruction: String?,
    val thumbnailLink: String?,
    val tags: String?,
    val instructionVideo: String?,
    val ingredient: List<String?>?,
    val measure: List<String?>?
) {

    fun getLinkPreview() = "$thumbnailLink/preview"
}
