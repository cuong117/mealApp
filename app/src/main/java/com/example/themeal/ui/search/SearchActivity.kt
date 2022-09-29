package com.example.themeal.ui.search

import android.os.Bundle
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import com.example.themeal.base.BaseActivity
import com.example.themeal.data.model.RecentSearch
import com.example.themeal.databinding.ActivitySearchBinding
import com.example.themeal.util.addFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchActivity : BaseActivity<ActivitySearchBinding>(ActivitySearchBinding::inflate) {

    private val searchViewModel by viewModel<SearchViewModel>()
    private val resultViewModel by viewModel<SearchResultViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchViewModel.getAllKeyWord()
        binding.search.onActionViewExpanded()
        addFragment(binding.layoutContainer.id, SearchFragment.newInstance { text, isSubmit ->
            binding.search.setQuery(text, isSubmit)
        }, false)
        addListener()
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
                        resultViewModel.searchMeal(it)
                        supportFragmentManager.popBackStack()
                        addFragment(
                            binding.layoutContainer.id,
                            SearchResultFragment.newInstance(),
                            false
                        )
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
