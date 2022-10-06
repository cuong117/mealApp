package com.example.themeal.ui.ingredientdetail

import android.os.Bundle
import android.widget.Toast
import com.example.themeal.R
import com.example.themeal.base.BaseActivity
import com.example.themeal.constant.Constant
import com.example.themeal.data.model.Ingredient
import com.example.themeal.databinding.ActivityIngredientDetailBinding
import com.example.themeal.ui.ingredient.IngredientViewModel
import com.example.themeal.util.loadImage
import org.koin.androidx.viewmodel.ext.android.viewModel

class IngredientDetailActivity :
    BaseActivity<ActivityIngredientDetailBinding>(ActivityIngredientDetailBinding::inflate) {

    private val viewModel by viewModel<IngredientViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.imageBack.setOnClickListener {
            onBackPressed()
        }
        initData()
        viewModel.isShowLoading.observe(this) {
            if (it) {
                loadingDialog.showLoadingDialog()
            } else {
                loadingDialog.dismiss()
            }
        }
    }

    private fun initData() {
        var ingredient = intent.getParcelableExtra<Ingredient>(Constant.KEY_INGREDIENT)
        updateDataView(ingredient)
        if (ingredient == null) {
            val ingredientName = intent.getStringExtra(Constant.KEY_INGREDIENT_NAME)
            ingredientName?.let { name ->
                viewModel.listIngredient.observe(this) {
                    ingredient = viewModel.findIngredient(name)
                    updateDataView(ingredient)
                    if (ingredient == null) {
                        Toast.makeText(
                            this,
                            getString(R.string.notification_not_found),
                            Toast.LENGTH_SHORT
                        ).show()
                        onBackPressed()
                    }
                }
            }
        }
    }

    private fun updateDataView(ingredient: Ingredient?) {
        ingredient?.let {
            binding.imageIngredient.loadImage(this, it.getThumbnail())
            binding.textName.text = it.name
            binding.textInstructionContent.text = it.description ?: ""
        }
    }
}
