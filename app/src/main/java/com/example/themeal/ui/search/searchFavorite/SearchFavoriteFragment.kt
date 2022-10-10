package com.example.themeal.ui.search.searchFavorite

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.example.themeal.base.BaseFragment
import com.example.themeal.constant.Constant
import com.example.themeal.data.model.FavoriteMeal
import com.example.themeal.data.model.MealCollapse
import com.example.themeal.databinding.FragmentSearchFavoriteBinding
import com.example.themeal.ui.favorite.FavoriteViewModel
import com.example.themeal.ui.mealdetail.MealDetailActivity
import com.example.themeal.ui.search.adapter.SearchFavoriteAdapter
import com.example.themeal.util.OnClickListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFavoriteFragment :
    BaseFragment<FragmentSearchFavoriteBinding>(FragmentSearchFavoriteBinding::inflate),
    OnClickListener<MealCollapse> {

    private val favoriteViewModel by viewModel<FavoriteViewModel>()
    private val favoriteMealList = mutableListOf<FavoriteMeal>()
    private val adapter by lazy { SearchFavoriteAdapter() }
    private var queryText: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favoriteViewModel.listSearchResult.observe(viewLifecycleOwner) {
            hideElement(it.isNotEmpty())
            adapter.submitList(it.toMutableList())
        }
        favoriteViewModel.isShowLoading.observe(viewLifecycleOwner) {
            if (it) {
                loadingDialog?.showLoadingDialog()
            } else {
                loadingDialog?.dismiss()
            }
        }
        adapter.updateListener(this)
        binding.recyclerSearchResult.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        queryText?.let { text ->
            favoriteViewModel.getAllFavoriteMeal()
            favoriteViewModel.listFavorite.observe(viewLifecycleOwner) {
                favoriteMealList.clear()
                favoriteMealList.addAll(it)
            }
            favoriteViewModel.listMeal.observe(viewLifecycleOwner) {
                favoriteViewModel.searchMealFavorite(text)
            }
        }
    }

    override fun onClick(data: MealCollapse) {
        val intent = Intent(activity, MealDetailActivity::class.java)
        intent.putExtra(Constant.KEY_MEAL, data)
        startActivity(intent)
    }

    override fun onItemClick(data: MealCollapse) {
        val favorite = favoriteMealList.find { it.mealId == data.id }
        favorite?.let {
            favoriteViewModel.deleteFavorite(favorite)
        }
    }

    private fun hideElement(isVisible: Boolean) {
        binding.recyclerSearchResult.isVisible = isVisible
        binding.textNoResult.isVisible = isVisible.not()
    }

    companion object {
        fun newInstance(queryText: String) =
            SearchFavoriteFragment().apply { this.queryText = queryText }
    }
}
