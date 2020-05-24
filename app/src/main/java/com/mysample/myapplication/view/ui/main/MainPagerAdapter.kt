package com.mysample.myapplication.view.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.mysample.myapplication.LOG_TAG
import timber.log.Timber

class MainPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                HomeFragment()
            }
            1 -> {
                HomeFragment()
            }
            else -> {
                HomeFragment()
            }
        }
    }

    override fun getCount(): Int = 1
}