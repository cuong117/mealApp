package com.example.themeal.ui.ingredient

import android.os.Bundle
import android.view.View
import com.example.themeal.R
import com.example.themeal.base.BaseFragment
import com.example.themeal.databinding.FragmentIngredientBinding
import com.example.themeal.ui.ingredient.adapter.IngredientAdapter
import com.example.themeal.util.RecyclerViewLoadMore
import org.koin.androidx.viewmodel.ext.android.viewModel

class IngredientFragment :
    BaseFragment<FragmentIngredientBinding>(FragmentIngredientBinding::inflate),
    RecyclerViewLoadMore {

    private val adapter by lazy { IngredientAdapter() }
    private val viewModel by viewModel<IngredientViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.currentList.observe(viewLifecycleOwner) {
            adapter.submitList(it.toMutableList())
        }
        binding.recyclerIngredient.adapter = adapter
        binding.searchLayout.textHint.text = getString(R.string.hint_search_ingredient)
        startLoadMore(binding.recyclerIngredient)
    }

    override fun loadMore() {
        viewModel.getNextItem()
    }

    override fun isLoadMore() = viewModel.isLoadMore
}
