package com.example.themeal.ui.home

import android.os.Bundle
import android.view.View
import com.example.themeal.base.BaseFragment
import com.example.themeal.constant.Constant
import com.example.themeal.data.model.HomeItem
import com.example.themeal.databinding.FragmentHomeBinding
import com.example.themeal.ui.home.adapter.HomeAdapter
import org.koin.android.ext.android.inject

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val homeViewModel by inject<HomeViewModel>()
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
        addObserver()
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
