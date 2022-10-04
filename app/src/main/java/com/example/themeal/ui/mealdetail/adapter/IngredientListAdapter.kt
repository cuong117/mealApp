package com.example.themeal.ui.mealdetail.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.example.themeal.base.BaseAdapter
import com.example.themeal.base.BaseViewHolder
import com.example.themeal.databinding.ItemIngredientBinding
import com.example.themeal.util.OnClickListener

class IngredientListAdapter :
    BaseAdapter<Pair<String, String>, ItemIngredientBinding, IngredientListAdapter.ViewHolder>(
        object : DiffUtil.ItemCallback<Pair<String, String>>() {

            override fun areItemsTheSame(
                oldItem: Pair<String, String>,
                newItem: Pair<String, String>
            ): Boolean {
                return oldItem.first == newItem.first
            }

            override fun areContentsTheSame(
                oldItem: Pair<String, String>,
                newItem: Pair<String, String>
            ): Boolean {
                return oldItem.first == newItem.first && oldItem.second == newItem.second
            }
        }
    ) {

    private var listener: OnClickListener<Pair<String, String>>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemIngredientBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position % 2 != 0) {
            holder.binding.root.setBackgroundColor(Color.WHITE)
            holder.setIsRecyclable(false)
        }
        holder.onBindData(currentList[position])
    }

    fun updateListener(listener: OnClickListener<Pair<String, String>>) {
        this.listener = listener
    }

    inner class ViewHolder(binding: ItemIngredientBinding) :
        BaseViewHolder<Pair<String, String>, ItemIngredientBinding>(binding), View.OnClickListener {

        private var data: Pair<String, String>? = null

        init {
            binding.root.setOnClickListener(this)
        }

        override fun onBindData(data: Pair<String, String>) {
            this.data = data
            binding.textName.text = data.first
            binding.textMeasure.text = data.second
        }

        override fun onClick(v: View?) {
            data?.let {
                listener?.onClick(it)
            }
        }
    }
}
