package com.example.themeal.ui.search

import android.os.Bundle
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import com.example.themeal.base.BaseActivity
import com.example.themeal.constant.Constant
import com.example.themeal.data.model.RecentSearch
import com.example.themeal.databinding.ActivitySearchBinding
import com.example.themeal.ui.ingredient.IngredientViewModel
import com.example.themeal.ui.search.searchFavorite.SearchFavoriteFragment
import com.example.themeal.ui.search.searchIngredient.IngredientResultFragment
import com.example.themeal.ui.search.searchmeal.SearchResultFragment
import com.example.themeal.ui.search.searchmeal.SearchResultViewModel
import com.example.themeal.util.addFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchActivity : BaseActivity<ActivitySearchBinding>(ActivitySearchBinding::inflate) {

    private val searchViewModel by viewModel<SearchViewModel>()
    private val resultViewModel by viewModel<SearchResultViewModel>()
    private val ingredientViewModel by viewModel<IngredientViewModel>()
    private var actionSubmit: ((String) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchViewModel.getAllKeyWord()
        binding.search.onActionViewExpanded()
        initData()
        addFragment(
            binding.layoutContainer.id,
            SearchFragment.newInstance { text, isSubmit ->
                binding.search.setQuery(text, isSubmit)
            },
            false
        )
        addListener()
    }

    private fun initData() {
        when (intent.getIntExtra(Constant.KEY_SEARCH_TYPE, -1)) {
            Constant.DATA_TYPE -> actionSubmit = { text ->
                resultViewModel.searchMeal(text)
                addFragment(
                    binding.layoutContainer.id,
                    SearchResultFragment.newInstance(),
                    false
                )
            }
            Constant.INGREDIENT_TYPE -> actionSubmit = { text ->
                ingredientViewModel.listIngredient.observe(this) {
                    ingredientViewModel.searchIngredient(text)
                }
                addFragment(
                    binding.layoutContainer.id,
                    IngredientResultFragment.newInstance(),
                    false
                )
            }
            Constant.FAVORITE_TYPE -> actionSubmit = { text ->
                addFragment(
                    binding.layoutContainer.id,
                    SearchFavoriteFragment.newInstance(text),
                    false
                )
            }
        }
    }

    private fun addListener() {

        binding.search.setOnQueryTextFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                addFragment(
                    binding.layoutContainer.id,
                    SearchFragment.newInstance { text, isSubmit ->
                        binding.search.setQuery(text, isSubmit)
                    },
                    true
                )
            }
        }

        binding.search.setOnQueryTextListener(object : OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    if (it.isNotBlank()) {
                        actionSubmit?.invoke(it)
                        supportFragmentManager.popBackStack()
                        searchViewModel.insertKeyWord(RecentSearch(it))
                        binding.search.clearFocus()
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    if (it.isBlank()) {
                        searchViewModel.addAllKeyWord()
                    } else {
                        searchViewModel.filterKeyWord(it)
                    }
                }
                return true
            }
        })

        binding.imageBack.setOnClickListener {
            binding.search.clearFocus()
            onBackPressed()
        }
    }
}
