package com.example.themeal.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.viewbinding.ViewBinding
import com.example.themeal.base.BaseAdapter
import com.example.themeal.base.BaseViewHolder
import com.example.themeal.data.model.Category
import com.example.themeal.data.model.HomeItem
import com.example.themeal.data.model.MealCollapse
import com.example.themeal.databinding.ListCategoryBinding
import com.example.themeal.databinding.ListMealBinding
import com.example.themeal.util.OnClickListener

class HomeAdapter :
    BaseAdapter<HomeItem, ViewBinding, BaseViewHolder<HomeItem, ViewBinding>>(HomeItem.getDiffUtil()) {

    private var listener: OnClickListener<Any>? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<HomeItem, ViewBinding> {
        val layoutInflater = LayoutInflater.from(parent.context)
        if (viewType == CATEGORY) {
            return CategoryViewHolder(
                ListCategoryBinding.inflate(layoutInflater, parent, false)
            )
        }
        return MealViewHolder(ListMealBinding.inflate(layoutInflater, parent, false))
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        if (item.name.isEmpty()) {
            return CATEGORY
        }
        return MEAL
    }

    fun updateListener(
        listener: OnClickListener<Any>
    ) {
        this.listener = listener
    }

    inner class MealViewHolder(
        override val binding: ListMealBinding
    ) : BaseViewHolder<HomeItem, ViewBinding>(binding), View.OnClickListener {

        private var data: HomeItem? = null

        init {
            listener?.let {
                binding.imageDirection.setOnClickListener(this)
            }
        }

        override fun onBindData(data: HomeItem) {
            this.data = data
            if (data.listItem.isEmpty()) {
                onBindEmptyData(data)
            } else {
                val listMeal = data.listItem.mapNotNull { it as? MealCollapse }
                val mealAdapter = MealAdapter()
                mealAdapter.submitList(listMeal)
                binding.recyclerMeal.adapter = mealAdapter
                setStateView(true, data.name)
            }
        }

        private fun onBindEmptyData(data: HomeItem) {
            setStateView(false, data.name)
        }

        private fun setStateView(isShowList: Boolean, name: String) {
            binding.textTitle.text = name
            binding.recyclerMeal.isVisible = isShowList
            binding.textEmpty.isVisible = isShowList.not()
        }

        override fun onClick(v: View?) {
            data?.let {
                listener?.onItemClick(it)
            }
        }
    }

    inner class CategoryViewHolder(
        override val binding: ListCategoryBinding
    ) : BaseViewHolder<HomeItem, ViewBinding>(binding) {

        override fun onBindData(data: HomeItem) {
            val listCategory = data.listItem.mapNotNull { it as? Category }
            val categoryAdapter = CategoryAdapter()
            categoryAdapter.updateListener(listener)
            categoryAdapter.submitList(listCategory)
            binding.recyclerCategory.adapter = categoryAdapter
        }
    }

    companion object {
        private const val CATEGORY = 0
        private const val MEAL = 1
    }
}
