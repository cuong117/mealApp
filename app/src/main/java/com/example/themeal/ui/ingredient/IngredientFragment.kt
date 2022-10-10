package com.example.themeal.ui.ingredient

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.themeal.R
import com.example.themeal.base.BaseFragment
import com.example.themeal.constant.Constant
import com.example.themeal.data.model.Ingredient
import com.example.themeal.databinding.FragmentIngredientBinding
import com.example.themeal.ui.ingredient.adapter.IngredientAdapter
import com.example.themeal.ui.ingredientdetail.IngredientDetailActivity
import com.example.themeal.ui.search.SearchActivity
import com.example.themeal.util.OnClickListener
import com.example.themeal.util.RecyclerViewLoadMore
import org.koin.androidx.viewmodel.ext.android.viewModel

class IngredientFragment :
    BaseFragment<FragmentIngredientBinding>(FragmentIngredientBinding::inflate),
    RecyclerViewLoadMore,
    OnClickListener<Ingredient> {

    private val adapter by lazy { IngredientAdapter() }
    private val viewModel by viewModel<IngredientViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.currentList.observe(viewLifecycleOwner) {
            adapter.submitList(it.toMutableList())
        }
        viewModel.isShowLoading.observe(viewLifecycleOwner) {
            if (it) {
                loadingDialog?.showLoadingDialog()
            } else {
                loadingDialog?.dismiss()
            }
        }
        binding.recyclerIngredient.adapter = adapter
        binding.searchLayout.textHint.text = getString(R.string.hint_search_ingredient)
        startLoadMore(binding.recyclerIngredient)
        adapter.updateListener(this)
        addListener()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllItem()
    }

    override fun loadMore() {
        viewModel.getNextItem()
    }

    override fun isLoadMore() = viewModel.isLoadMore

    override fun onClick(data: Ingredient) {
        val intent = Intent(activity, IngredientDetailActivity::class.java)
        intent.putExtra(Constant.KEY_INGREDIENT, data)
        startActivity(intent)
    }

    private fun addListener() {
        binding.searchLayout.searchContainer.setOnClickListener {
            val intent = Intent(activity, SearchActivity::class.java)
            intent.putExtra(Constant.KEY_SEARCH_TYPE, Constant.INGREDIENT_TYPE)
            startActivity(intent)
        }
    }
}
