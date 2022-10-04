package com.example.themeal.data.model

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.themeal.data.source.database.MealDatabase
import kotlinx.parcelize.Parcelize

@Parcelize
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
) : Parcelable {

    fun getLinkPreview() = "$thumbnailLink/preview"

    fun getIngredientMeasure(): List<Pair<String, String>> {
        val list = mutableListOf<Pair<String, String>>()
        val length = ingredient?.size ?: 0
        for (i in 0 until length) {
            ingredient?.get(i)?.let {
                if (it.isNotBlank()) {
                    list.add(it to (measure?.get(i) ?: ""))
                }
            }
        }
        list.sortBy { it.first }
        return list
    }

    fun getVideoId(): String {
        var result = ""
        instructionVideo?.let {
            result = it.substringAfter(DELIMITER)
        }
        return result
    }

    companion object {

        private const val DELIMITER = "="

        fun getDiffUtil() = object : DiffUtil.ItemCallback<MealCollapse>() {
            override fun areItemsTheSame(oldItem: MealCollapse, newItem: MealCollapse): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MealCollapse, newItem: MealCollapse): Boolean {
                return oldItem == newItem
            }
        }
    }
}
