package com.mysample.myapplication.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mysample.disneymotions.model.Poster
import com.mysample.myapplication.LOG_TAG
import com.mysample.myapplication.view.ui.main.adapter.PosterAdapter
import com.skydoves.baserecyclerviewadapter.BaseAdapter
import com.skydoves.whatif.whatIfNotNullOrEmpty
import timber.log.Timber

@BindingAdapter("adapterPosterLIst")
fun bindAdapterPosterLIst(view: RecyclerView, posters: List<Poster>?) {
    posters.whatIfNotNullOrEmpty {
        (view.adapter as? PosterAdapter)?.addPosterList(it)
    }
}

@BindingAdapter("adapter")
fun bindAdapter(view: RecyclerView, baseAdapter: BaseAdapter) {
    view.adapter = baseAdapter
}

