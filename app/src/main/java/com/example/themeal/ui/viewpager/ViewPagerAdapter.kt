package com.example.themeal.ui.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.themeal.ui.favorite.FavoriteFragment
import com.example.themeal.ui.home.HomeFragment
import com.example.themeal.ui.ingredient.IngredientFragment
import org.koin.java.KoinJavaComponent.inject

class ViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    private val listFragment = listOf<Fragment>(
        inject<HomeFragment>(HomeFragment::class.java).value,
        inject<IngredientFragment>(IngredientFragment::class.java).value,
        inject<FavoriteFragment>(FavoriteFragment::class.java).value
    )

    override fun getItemCount() = listFragment.size

    override fun createFragment(position: Int): Fragment {
        return listFragment[position]
    }
}
