package com.example.themeal.base

import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding
import java.util.concurrent.Executors

abstract class BaseAdapter<T, VB: ViewBinding>(
    diffCallback: DiffUtil.ItemCallback<T>
) : ListAdapter<T, BaseViewHolder<T, VB>>(
    AsyncDifferConfig.Builder(diffCallback)
        .setBackgroundThreadExecutor(Executors.newSingleThreadExecutor())
        .build()
) {

    override fun onBindViewHolder(holder: BaseViewHolder<T, VB>, position: Int) {
        holder.onBindData(getItem(position))
    }
}
