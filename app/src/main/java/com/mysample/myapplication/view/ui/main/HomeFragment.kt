package com.mysample.myapplication.view.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.mysample.myapplication.R
import com.mysample.myapplication.databinding.FragmentHomeBinding
import com.mysample.myapplication.view.ui.main.adapter.PosterAdapter

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentHomeBinding>(inflater, R.layout.fragment_home, container, false)
        val view = binding.apply {
            viewModel = MainViewModel().apply {
                fetchDisneyPosterList()
            }

            lifecycleOwner = this@HomeFragment
            adapter = PosterAdapter()

        }.root

        return view
    }


}