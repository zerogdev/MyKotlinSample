package com.mysample.myapplication.view.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.mysample.myapplication.R
import com.mysample.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this,
            R.layout.activity_main
        ) //델리게이트로도 변경가능(ContentViewBindingDelegate)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.apply {
            pagerAdapter = MainPagerAdapter(supportFragmentManager)
            navigation = mainBottomNavigation
        }
    }
}
