package com.example.themeal.ui.listmeal

import android.os.Bundle
import androidx.core.view.isVisible
import com.example.themeal.base.BaseActivity
import com.example.themeal.constant.Constant
import com.example.themeal.data.model.MealCollapse
import com.example.themeal.databinding.ActivityListMealBinding
import com.example.themeal.ui.listmeal.adapter.ListMealAdapter
import com.example.themeal.util.OnClickListener
import com.example.themeal.util.RecyclerViewLoadMore
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListMealActivity :
    BaseActivity<ActivityListMealBinding>(ActivityListMealBinding::inflate),
    OnClickListener<Any>,
    RecyclerViewLoadMore {

    private val adapter by lazy { ListMealAdapter() }
    private val viewModel by viewModel<ListMealViewModel>()
    private var isLoadMoreOffline = false
    private var isLoadMoreOnline = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var title = intent.getStringExtra(Constant.TITLE_TOOL_BAR)
        var list = mutableListOf<MealCollapse>()

        if (title == null) {
            title = intent.getStringExtra(Constant.KEY_CATEGORY_DATA) ?: ""
            isLoadMoreOffline = true
            viewModel.getMealByCategory(title)
            startLoadMore(binding.recyclerListMeal)
        } else {
            list = intent.getParcelableArrayListExtra<MealCollapse>(Constant.KEY_LIST_DATA)
                ?.toMutableList() ?: mutableListOf()
            isLoadMoreOnline = title == Constant.ALL_LIST
            if (isLoadMoreOnline) {
                viewModel.getAllMeal()
                startLoadMore(binding.recyclerListMeal)
            }
        }

        addObserver()

        hideElement(list.isEmpty())

        setSupportActionBar(binding.toolBar)
        supportActionBar?.title = title
        binding.recyclerListMeal.adapter = adapter
        adapter.submitList(list)
        addListener()
    }

    override fun loadMore() {
        if (isLoadMoreOnline) {
            viewModel.getAllMeal()
        } else {
            viewModel.getNextItem()
        }
    }

    override fun isLoadMore() =
        if (isLoadMoreOnline) viewModel.isAllMealLoadMore else viewModel.isLoadMore

    private fun hideElement(isVisible: Boolean) {
        binding.recyclerListMeal.isVisible = isVisible.not()
        binding.textEmpty.isVisible = isVisible
    }

    private fun addListener() {
        binding.toolBar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun addObserver() {
        if (isLoadMoreOnline) {
            viewModel.listAllMeal.observe(this) {
                updateState(it)
            }
        } else {
            viewModel.currentList.observe(this) {
                updateState(it)
            }
        }
    }

    private fun updateState(list: List<MealCollapse>) {
        hideElement(list.isEmpty())
        adapter.submitList(list.toMutableList())
    }
}
