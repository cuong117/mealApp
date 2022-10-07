package com.example.themeal.ui.search.searchIngredient

import android.os.Bundle
import android.view.View
import com.example.themeal.base.BaseFragment
import com.example.themeal.databinding.FragmentIngredientResultBinding
import com.example.themeal.ui.ingredient.IngredientViewModel
import com.example.themeal.ui.search.adapter.IngredientResultAdapter
import com.example.themeal.util.RecyclerViewLoadMore
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class IngredientResultFragment :
    BaseFragment<FragmentIngredientResultBinding>(FragmentIngredientResultBinding::inflate), RecyclerViewLoadMore {

    private val ingredientViewModel by sharedViewModel<IngredientViewModel>()
    private val resultViewModel by viewModel<IngredientResultViewModel>()
    private val adapter by lazy { IngredientResultAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ingredientViewModel.listIngredientResult.observe(viewLifecycleOwner) {
            resultViewModel.addList(it)
        }
        resultViewModel.currentList.observe(viewLifecycleOwner) {
            adapter.submitList(it.toMutableList())
        }
        binding.recyclerIngredient.adapter = adapter
        startLoadMore(binding.recyclerIngredient)
    }

    override fun loadMore() {
        resultViewModel.getNextItem()
    }

    override fun isLoadMore() = resultViewModel.isLoadMore

    companion object {
        fun newInstance() = IngredientResultFragment()
    }
}
