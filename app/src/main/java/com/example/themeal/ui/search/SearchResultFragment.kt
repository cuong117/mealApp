package com.example.themeal.ui.search

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.core.view.isVisible
import com.example.themeal.base.BaseFragment
import com.example.themeal.databinding.FragmentSearchResultBinding
import com.example.themeal.ui.home.HomeViewModel
import com.example.themeal.ui.ingredient.IngredientViewModel
import com.example.themeal.ui.search.adapter.SearchResultAdapter
import com.example.themeal.util.RecyclerViewLoadMore
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchResultFragment :
    BaseFragment<FragmentSearchResultBinding>(FragmentSearchResultBinding::inflate),
    RecyclerViewLoadMore {

    private val viewModel by sharedViewModel<SearchResultViewModel>()
    private val homeViewModel by viewModel<HomeViewModel>()
    private val ingredientViewModel by viewModel<IngredientViewModel>()
    private val adapter by lazy { SearchResultAdapter() }
    private var isInit = true
    private var category: String? = null
    private var area: String? = null
    private var ingredient: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerSearchResult.adapter = adapter
        viewModel.getArea()
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

    }

    override fun loadMore() {
        viewModel.getNextItem()
    }

    override fun isLoadMore() = viewModel.isLoadMore

    private fun addObserver() {
        viewModel.currentList.observe(viewLifecycleOwner) {
            hideElement(it.isEmpty())
            if (isInit) {
                adapter.submitList(it.toMutableList())
                isInit = false
            } else {
                adapter.submitList(it.toMutableList(), viewModel.isLoadMore)
            }
        }

        homeViewModel.categoryList.observe(viewLifecycleOwner) {
            val list = mutableListOf(CATEGORY)
            list.addAll(it.map { category -> category.name })
            initSpinner(binding.spinnerCategory, list)
        }

        ingredientViewModel.listIngredient.observe(viewLifecycleOwner) {
            val list = mutableListOf(INGREDIENT)
            list.addAll(it.map { ingredient -> ingredient.name })
            initSpinner(binding.spinnerIngredient, list)
        }

        viewModel.myListArea.observe(viewLifecycleOwner) {
            val list = mutableListOf(AREA)
            list.addAll(it.map { area -> area.name })
            initSpinner(binding.spinnerArea, list)
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
