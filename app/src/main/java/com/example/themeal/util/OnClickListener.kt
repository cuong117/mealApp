package com.example.themeal.util

import android.view.View

interface OnClickListener<T> {

    fun onClick(data: T) {}

    fun onLongClick(data: T) {}

    fun onItemClick(data: T) {}

    fun onItemClick(view: View, data: T) {}
}
