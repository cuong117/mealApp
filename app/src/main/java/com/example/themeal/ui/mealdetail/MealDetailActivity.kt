package com.example.themeal.ui.mealdetail

import android.content.Intent
import android.os.Bundle
import com.example.themeal.R
import com.example.themeal.base.BaseActivity
import com.example.themeal.constant.Constant
import com.example.themeal.data.model.FavoriteMeal
import com.example.themeal.data.model.MealCollapse
import com.example.themeal.databinding.ActivityMealDetailBinding
import com.example.themeal.ui.favorite.FavoriteViewModel
import com.example.themeal.ui.ingredientdetail.IngredientDetailActivity
import com.example.themeal.ui.mealdetail.adapter.IngredientListAdapter
import com.example.themeal.util.OnClickListener
import com.example.themeal.util.loadImage
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class MealDetailActivity :
    BaseActivity<ActivityMealDetailBinding>(ActivityMealDetailBinding::inflate),
    OnClickListener<Pair<String, String>> {

    private val mealViewModel by viewModel<MealDetailViewModel>()
    private val favoriteViewModel by viewModel<FavoriteViewModel>()
    private val adapter by lazy { IngredientListAdapter() }
    private var meal: MealCollapse? = null
    private var isFavorite = false
    private var listFavoriteMeal: List<FavoriteMeal>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter.updateListener(this)
        initData()
        binding.imageBack.setOnClickListener { onBackPressed() }
        binding.imageFavorite.setOnClickListener {
            meal?.let { meal ->
                if (isFavorite) {
                    listFavoriteMeal?.find { it.mealId == meal.id }?.let {
                        favoriteViewModel.deleteFavorite(it)
                        isFavorite = false
                    }
                } else {
                    favoriteViewModel.insertNewFavorite(FavoriteMeal(meal.id))
                    isFavorite = true
                }
            }
        }
        favoriteViewModel.isInsertSuccess.observe(this) {
            isFavorite = it
            updateFavoriteState()
        }
        favoriteViewModel.isDeleteSuccess.observe(this) {
            isFavorite = it.not()
            updateFavoriteState()
        }
        mealViewModel.isShowLoading.observe(this) {
            if (it) {
                loadingDialog.showLoadingDialog()
            } else {
                loadingDialog.dismiss()
            }
        }
    }

    override fun onClick(data: Pair<String, String>) {
        val intent = Intent(this, IngredientDetailActivity::class.java)
        intent.putExtra(Constant.KEY_INGREDIENT_NAME, data.first)
        startActivity(intent)
    }

    private fun initData() {
        meal = intent.getParcelableExtra(Constant.KEY_MEAL)
        val isLossData = intent.getBooleanExtra(Constant.KEY_LOSS_DATA, false)
        if (isLossData) {
            meal?.let { mealCollapse ->
                mealViewModel.getMeal(mealCollapse.id)
                mealViewModel.meal.observe(this) {
                    meal = it.first()
                    initStateView(meal)
                }
            }
        } else {
            initStateView(meal)
        }
    }

    private fun initStateView(meal: MealCollapse?) {
        meal?.let {
            mealViewModel.insertRecentMeal(it)
        }
        favoriteViewModel.getAllFavoriteMeal()
        favoriteViewModel.listFavorite.observe(this) {
            listFavoriteMeal = it
            it.forEach { favMeal ->
                if (favMeal.mealId == meal?.id) {
                    isFavorite = true
                    updateFavoriteState()
                }
            }
        }
        meal?.let {
            binding.textName.text = it.name
            binding.textTag.text = getString(R.string.tags, it.tags ?: "")
            binding.textArea.text = getString(R.string.area, it.area ?: "")
            binding.textInstructionContent.text = it.instruction ?: ""
        }
        adapter.submitList(meal?.getIngredientMeasure())
        binding.recyclerIngredient.adapter = adapter
        meal?.thumbnailLink?.let {
            binding.imageMeal.loadImage(this, it)
        }
        lifecycle.addObserver(binding.videoInstruction)
        binding.videoInstruction.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {

            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoId = meal?.getVideoId()
                if (videoId.isNullOrBlank().not()) {
                    videoId?.let { youTubePlayer.cueVideo(it, 0F) }
                }
            }
        })
    }

    private fun updateFavoriteState() {
        if (isFavorite) {
            binding.imageFavorite.setImageResource(R.drawable.ic_favorite_solid)
        } else {
            binding.imageFavorite.setImageResource(R.drawable.ic_favorite)
        }
    }
}
