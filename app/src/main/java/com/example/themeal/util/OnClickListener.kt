package com.example.themeal.util

interface OnClickListener<T> {

    fun onClick(data: T)

    fun onLongClick(data: T)

    fun onItemClick(data: T)
}
