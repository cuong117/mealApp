package com.example.themeal.ui.search.searchmeal

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.core.view.isVisible
import com.example.themeal.base.BaseFragment
import com.example.themeal.constant.Constant
import com.example.themeal.data.model.FavoriteMeal
import com.example.themeal.data.model.MealCollapse
import com.example.themeal.databinding.FragmentSearchResultBinding
import com.example.themeal.ui.favorite.FavoriteViewModel
import com.example.themeal.ui.home.HomeViewModel
import com.example.themeal.ui.ingredient.IngredientViewModel
import com.example.themeal.ui.mealdetail.MealDetailActivity
import com.example.themeal.ui.search.adapter.SearchResultAdapter
import com.example.themeal.util.OnClickListener
import com.example.themeal.util.RecyclerViewLoadMore
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchResultFragment :
    BaseFragment<FragmentSearchResultBinding>(FragmentSearchResultBinding::inflate),
    RecyclerViewLoadMore, OnClickListener<MealCollapse> {

    private val viewModel by sharedViewModel<SearchResultViewModel>()
    private val homeViewModel by viewModel<HomeViewModel>()
    private val ingredientViewModel by viewModel<IngredientViewModel>()
    private val favoriteViewModel by viewModel<FavoriteViewModel>()
    private var listFavorite = mutableListOf<FavoriteMeal>()
    private var listMealResult = mutableListOf<MealCollapse>()
    private val adapter by lazy { SearchResultAdapter() }
    private var isInitMeal = false
    private var isInitFavorite = false
    private var category: String? = null
    private var area: String? = null
    private var ingredient: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getArea()
        binding.recyclerSearchResult.adapter = adapter
        hideElement(true)
        addObserver()
        startLoadMore(binding.recyclerSearchResult)
        addSpinnerListener(binding.spinnerArea) {
            area = it
            viewModel.mealFilter(area, ingredient, category)
        }
        addSpinnerListener(binding.spinnerIngredient) {
            ingredient = it
            viewModel.mealFilter(area, ingredient, category)
        }
        addSpinnerListener(binding.spinnerCategory) {
            category = it
            viewModel.mealFilter(area, ingredient, category)
        }
        adapter.updateListener(this)
    }

    override fun onStart() {
        super.onStart()
        isInitMeal = false
        favoriteViewModel.getAllFavoriteMeal()
    }

    override fun loadMore() {
        viewModel.getNextItem()
    }

    override fun isLoadMore() = viewModel.isLoadMore

    override fun onClick(data: MealCollapse) {
        val intent = Intent(activity, MealDetailActivity::class.java)
        intent.putExtra(Constant.KEY_MEAL, data)
        startActivity(intent)
    }

    override fun onItemClick(data: MealCollapse) {
        val favorite = listFavorite.find { it.mealId == data.id }
        if (favorite == null) {
            favoriteViewModel.insertNewFavorite(FavoriteMeal(data.id))
        } else {
            favoriteViewModel.deleteFavorite(favorite)
        }
    }

    private fun addObserver() {
        viewModel.isShowLoading.observe(viewLifecycleOwner) {
            if (it) {
                loadingDialog?.showLoadingDialog()
            } else {
                loadingDialog?.dismiss()
            }
        }
        viewModel.currentList.observe(viewLifecycleOwner) {
            hideElement(it.isEmpty())
            listMealResult = it.toMutableList()
            if (isInitMeal.not() && isInitFavorite) {
                adapter.submitList(listMealResult, false)
                isInitMeal = true
            } else if (isInitFavorite) {
                adapter.submitList(listMealResult, viewModel.isLoadMore)
            }

        }

        homeViewModel.categoryList.observe(viewLifecycleOwner) {
            val list = mutableListOf(CATEGORY)
            list.addAll(it.map { category -> category.name })
            initSpinner(binding.spinnerCategory, list)
        }

        ingredientViewModel.listIngredient.observe(viewLifecycleOwner) {
            val list = mutableListOf(INGREDIENT)
            list.addAll(it.map { ingredient -> ingredient.name ?: "" })
            initSpinner(binding.spinnerIngredient, list)
        }

        viewModel.myListArea.observe(viewLifecycleOwner) {
            val list = mutableListOf(AREA)
            list.addAll(it.map { area -> area.name })
            initSpinner(binding.spinnerArea, list)
        }

        favoriteViewModel.listFavorite.observe(viewLifecycleOwner) {
            listFavorite = it.toMutableList()
            isInitFavorite = true
            adapter.updateListFavorite(it.map { meal -> meal.mealId })
            adapter.submitList(listMealResult, false)
        }
    }

    private fun addSpinnerListener(spinner: Spinner, filter: (String?) -> Unit) {

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position > 0) {
                    filter(spinner.selectedItem.toString())
                } else {
                    filter(null)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // No-ip
            }
        }
    }

    private fun initSpinner(spinner: Spinner, list: List<String>) {
        context?.let {
            val arrayAdapter = ArrayAdapter(it, android.R.layout.simple_spinner_item, list)
            arrayAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice)
            spinner.adapter = arrayAdapter
        }
    }

    private fun hideElement(isVisible: Boolean) {
        binding.recyclerSearchResult.isVisible = isVisible.not()
        binding.textNoResult.isVisible = isVisible
    }

    companion object {
        private const val CATEGORY = "Category"
        private const val INGREDIENT = "Ingredient"
        private const val AREA = "Area"

        fun newInstance() = SearchResultFragment()
    }
}
