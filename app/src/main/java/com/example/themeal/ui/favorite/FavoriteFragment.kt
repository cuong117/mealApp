package com.example.themeal.ui.favorite

import android.content.Intent
import androidx.core.view.isVisible
import com.example.themeal.R
import com.example.themeal.base.BaseFragment
import com.example.themeal.constant.Constant
import com.example.themeal.data.model.MealCollapse
import com.example.themeal.databinding.FragmentFavoriteBinding
import com.example.themeal.ui.favorite.adapter.FavoriteAdapter
import com.example.themeal.ui.mealdetail.MealDetailActivity
import com.example.themeal.ui.search.SearchActivity
import com.example.themeal.util.OnClickListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment :
    BaseFragment<FragmentFavoriteBinding>(FragmentFavoriteBinding::inflate),
    OnClickListener<MealCollapse> {

    private val adapter by lazy { FavoriteAdapter() }
    private val viewModel by viewModel<FavoriteViewModel>()

    override fun onStart() {
        super.onStart()
        viewModel.getAllFavoriteMeal()
        viewModel.listMeal.observe(viewLifecycleOwner) {
            hideElement(it.isNotEmpty())
            adapter.submitList(it.toMutableList())
        }
        viewModel.isShowLoading.observe(viewLifecycleOwner) {
            if (it) {
                loadingDialog?.showLoadingDialog()
            } else {
                loadingDialog?.dismiss()
            }
        }
        binding.recyclerListMeal.adapter = adapter
        binding.searchLayout.textHint.text = getString(R.string.hint_search_favorite)
        adapter.updateListener(this)
        addListener()
    }

    override fun onClick(data: MealCollapse) {
        val intent = Intent(activity, MealDetailActivity::class.java)
        intent.putExtra(Constant.KEY_MEAL, data)
        startActivity(intent)
    }

    private fun hideElement(isVisible: Boolean) {
        binding.recyclerListMeal.isVisible = isVisible
        binding.textEmpty.isVisible = isVisible.not()
    }

    private fun addListener() {
        binding.searchLayout.searchContainer.setOnClickListener {
            val intent = Intent(activity, SearchActivity::class.java)
            intent.putExtra(Constant.KEY_SEARCH_TYPE, Constant.FAVORITE_TYPE)
            startActivity(intent)
        }
    }
}
