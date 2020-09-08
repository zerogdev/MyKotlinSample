package com.mysample.myapplication.view.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mysample.myapplication.R
import com.mysample.myapplication.databinding.FragmentLibraryBinding

class LibraryFragment : Fragment() {

    lateinit var viewModel: LibraryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentLibraryBinding>(inflater, R.layout.fragment_library, container, false)
        val view = binding.apply {

        }.root

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, LibraryModelFactory()).get(LibraryViewModel::class.java)
//        viewModel.test()
//        viewModel.testCancel()
        viewModel.testSuspendCancellableCoroutine()
    }
}

class LibraryModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LibraryViewModel() as T
    }
}