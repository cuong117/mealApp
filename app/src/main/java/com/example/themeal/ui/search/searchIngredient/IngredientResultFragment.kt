package com.example.themeal.ui.search.searchIngredient

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.example.themeal.base.BaseFragment
import com.example.themeal.constant.Constant
import com.example.themeal.data.model.Ingredient
import com.example.themeal.databinding.FragmentIngredientResultBinding
import com.example.themeal.ui.ingredient.IngredientViewModel
import com.example.themeal.ui.ingredientdetail.IngredientDetailActivity
import com.example.themeal.ui.search.adapter.IngredientResultAdapter
import com.example.themeal.util.OnClickListener
import com.example.themeal.util.RecyclerViewLoadMore
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class IngredientResultFragment :
    BaseFragment<FragmentIngredientResultBinding>(FragmentIngredientResultBinding::inflate),
    RecyclerViewLoadMore, OnClickListener<Ingredient> {

    private val ingredientViewModel by sharedViewModel<IngredientViewModel>()
    private val resultViewModel by viewModel<IngredientResultViewModel>()
    private val adapter by lazy { IngredientResultAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ingredientViewModel.listIngredientResult.observe(viewLifecycleOwner) {
            resultViewModel.addList(it)
        }
        resultViewModel.currentList.observe(viewLifecycleOwner) {
            hideElement(it.isNotEmpty())
            adapter.submitList(it.toMutableList(), resultViewModel.isLoadMore)
        }
        ingredientViewModel.isShowLoading.observe(viewLifecycleOwner) {
            if (it) {
                loadingDialog?.showLoadingDialog()
            } else {
                loadingDialog?.dismiss()
            }
        }
        binding.recyclerIngredient.adapter = adapter
        startLoadMore(binding.recyclerIngredient)
        adapter.updateListener(this)
    }

    override fun loadMore() {
        resultViewModel.getNextItem()
    }

    override fun isLoadMore() = resultViewModel.isLoadMore

    override fun onClick(data: Ingredient) {
        val intent = Intent(activity, IngredientDetailActivity::class.java)
        intent.putExtra(Constant.KEY_INGREDIENT, data)
        startActivity(intent)
    }

    private fun hideElement(isVisible: Boolean) {
        binding.recyclerIngredient.isVisible = isVisible
        binding.textNoResult.isVisible = isVisible.not()
    }

    companion object {
        fun newInstance() = IngredientResultFragment()
    }
}
