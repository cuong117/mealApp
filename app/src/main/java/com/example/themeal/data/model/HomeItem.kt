package com.example.themeal.data.model

import androidx.recyclerview.widget.DiffUtil

data class HomeItem(
    val name: String = "",
    val listItem: List<Any> = listOf()
) {
    companion object {

        fun getDiffUtil() = object : DiffUtil.ItemCallback<HomeItem>() {
            override fun areItemsTheSame(oldItem: HomeItem, newItem: HomeItem): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: HomeItem, newItem: HomeItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}
