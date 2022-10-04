package com.example.themeal.ui.mealdetail

import android.content.Intent
import android.os.Bundle
import com.example.themeal.R
import com.example.themeal.base.BaseActivity
import com.example.themeal.constant.Constant
import com.example.themeal.data.model.MealCollapse
import com.example.themeal.databinding.ActivityMealDetailBinding
import com.example.themeal.ui.ingredientdetail.IngredientDetailActivity
import com.example.themeal.ui.mealdetail.adapter.IngredientListAdapter
import com.example.themeal.util.OnClickListener
import com.example.themeal.util.loadImage
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class MealDetailActivity :
    BaseActivity<ActivityMealDetailBinding>(ActivityMealDetailBinding::inflate), OnClickListener<Pair<String, String>> {

    private val adapter by lazy { IngredientListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter.updateListener(this)
        initData()
        binding.imageBack.setOnClickListener { onBackPressed() }
    }

    override fun onClick(data: Pair<String, String>) {
        val intent = Intent(this, IngredientDetailActivity::class.java)
        intent.putExtra(Constant.KEY_INGREDIENT_NAME, data.first)
        startActivity(intent)
    }

    private fun initData() {
        val meal = intent.getParcelableExtra<MealCollapse>(Constant.KEY_MEAL)
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
}
