package com.example.themeal.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.themeal.R
import com.example.themeal.base.BaseFragment
import com.example.themeal.constant.Constant
import com.example.themeal.data.model.Category
import com.example.themeal.data.model.HomeItem
import com.example.themeal.data.model.MealCollapse
import com.example.themeal.databinding.FragmentHomeBinding
import com.example.themeal.ui.home.adapter.HomeAdapter
import com.example.themeal.ui.listmeal.ListMealActivity
import com.example.themeal.ui.mealdetail.MealDetailActivity
import com.example.themeal.ui.search.SearchActivity
import com.example.themeal.util.OnClickListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment :
    BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate), OnClickListener<Any> {

    private val homeViewModel by viewModel<HomeViewModel>()
    private val homeAdapter by lazy { HomeAdapter() }
    private var myListItem =
        listOf(
            HomeItem(),
            HomeItem(Constant.RECENT_LIST),
            HomeItem(Constant.NEW_LIST),
            HomeItem(Constant.ALL_LIST)
        )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerOuter.adapter = homeAdapter
        homeAdapter.submitList(myListItem)
        binding.searchLayout.textHint.text = getString(R.string.hint_search_view)
        addObserver()
        addListener()
        homeAdapter.updateListener(this)
    }

    override fun onStart() {
        super.onStart()
        homeViewModel.getListMealRecent()
    }

    override fun onItemClick(data: Any) {
        if (data is HomeItem) {
            val intent = Intent(activity, ListMealActivity::class.java)
            intent.putExtra(Constant.TITLE_TOOL_BAR, data.name)
            val list = arrayListOf<MealCollapse>()
            if (data.name != Constant.ALL_LIST) {
                list.addAll(data.listItem.mapNotNull { it as? MealCollapse })
            }
            intent.putParcelableArrayListExtra(Constant.KEY_LIST_DATA, list)
            startActivity(intent)
        }
    }

    override fun onClick(data: Any) {
        if (data is Category) {
            val intent = Intent(activity, ListMealActivity::class.java)
            intent.putExtra(Constant.KEY_CATEGORY_DATA, data.name)
            startActivity(intent)
        } else if (data is MealCollapse) {
            val intent = Intent(activity, MealDetailActivity::class.java)
            intent.putExtra(Constant.KEY_MEAL, data)
            startActivity(intent)
        }
    }

    private fun addListener() {
        binding.searchLayout.searchContainer.setOnClickListener {
            val intent = Intent(activity, SearchActivity::class.java)
            intent.putExtra(Constant.KEY_SEARCH_TYPE, Constant.DATA_TYPE)
            startActivity(intent)
        }
    }

    private fun addObserver() {
        homeViewModel.apply {
            allMealList.observe(viewLifecycleOwner) {
                submitNewList(Constant.ALL_LIST, it)
            }

            categoryList.observe(viewLifecycleOwner) {
                submitNewList(Constant.CATEGORY_LIST, it)
            }

            newMealList.observe(viewLifecycleOwner) {
                submitNewList(Constant.NEW_LIST, it)
            }

            recentMealList.observe(viewLifecycleOwner) {
                submitNewList(Constant.RECENT_LIST, it)
            }
            isShowLoading.observe(viewLifecycleOwner) {
                if (it) {
                    loadingDialog?.showLoadingDialog()
                } else {
                    loadingDialog?.dismiss()
                }
            }
        }
    }

    private fun submitNewList(name: String, list: List<Any>) {
        myListItem = myListItem.map {
            if (it.name == name) {
                it.copy(listItem = list)
            } else {
                it
            }
        }
        homeAdapter.submitList(myListItem)
    }
}
