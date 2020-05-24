package com.mysample.myapplication.view.ui.main.viewholder

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.mysample.disneymotions.model.Poster
import com.mysample.myapplication.databinding.ItemPosterBinding
import com.skydoves.baserecyclerviewadapter.BaseViewHolder

class PosterViewHolder(view: View) : BaseViewHolder(view) {

    private lateinit var data: Poster
    private val binding: ItemPosterBinding by lazy {
        requireNotNull(DataBindingUtil.bind<ItemPosterBinding>(view)) { "cannot find the matched view to layout." }
    }

    override fun bindData(data: Any) {
        if (data is Poster) {
            this.data = data
            drawItemUI()
        }
    }

    private fun drawItemUI() {
        binding.apply {
            poster = data
        }
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }

    override fun onLongClick(p0: View?): Boolean {
        TODO("Not yet implemented")
    }
}