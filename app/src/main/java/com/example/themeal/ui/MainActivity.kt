package com.example.themeal.ui

import android.os.Bundle
import com.example.themeal.R
import com.example.themeal.base.BaseActivity
import com.example.themeal.databinding.ActivityMainBinding
import com.example.themeal.ui.viewpager.ViewPagerAdapter

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private val listFragmentId = listOf(R.id.item_home, R.id.item_ingredient, R.id.item_favorite)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewPager.adapter = ViewPagerAdapter(this)
        binding.viewPager.isUserInputEnabled = false
        addListener()
    }

    private fun addListener() {
        binding.bottomNavView.setOnItemSelectedListener {
            binding.viewPager.currentItem = listFragmentId.indexOf(it.itemId)
            true
        }

        binding.bottomNavView.setOnItemReselectedListener {}
    }
}
