package com.example.themeal.util

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun AppCompatActivity.addFragment(
    containerId: Int,
    fragment: Fragment,
    isAddBackStack: Boolean = true
) {
    supportFragmentManager.beginTransaction().apply {
        add(containerId, fragment)
        if (isAddBackStack){
            addToBackStack(null)
        }
        commit()
    }
}
