package com.example.themeal.ui.ingredient

import android.os.Bundle
import android.view.View
import com.example.themeal.base.BaseFragment
import com.example.themeal.data.model.Ingredient
import com.example.themeal.databinding.FragmentIngredientBinding
import com.example.themeal.ui.ingredient.adapter.IngredientAdapter
import com.example.themeal.util.RecyclerViewLoadMore
import org.koin.android.ext.android.inject

class IngredientFragment :
    BaseFragment<FragmentIngredientBinding>(FragmentIngredientBinding::inflate),
    RecyclerViewLoadMore {

    private val adapter by lazy { IngredientAdapter() }
    private val viewModel by inject<IngredientViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getNextItem()
        viewModel.currentList.observe(viewLifecycleOwner) {
            adapter.submitList(it.toMutableList())
        }
        binding.recyclerIngredient.adapter = adapter

        startLoadMore(binding.recyclerIngredient)
    }

    override fun loadMore() {
        viewModel.getNextItem()
    }

    override fun isLoadMore() = viewModel.isLoadMore

}
