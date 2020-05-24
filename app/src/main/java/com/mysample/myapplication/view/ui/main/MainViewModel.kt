package com.mysample.myapplication.view.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.mysample.disneymotions.model.Poster

class MainViewModel constructor(
) {

    private var posterFetchingLiveData : MutableLiveData<Boolean> = MutableLiveData()
    val posterListLiveData : LiveData<List<Poster>>

    init {

        this.posterListLiveData = this.posterFetchingLiveData.switchMap {
            val liveData = MutableLiveData<List<Poster>>()
            var posters = makeDummyData()
            liveData.apply {
                postValue(posters)
            }
        }
    }

    fun fetchDisneyPosterList() {
        this.posterFetchingLiveData.postValue(true)
    }

    private fun makeDummyData() : List<Poster> {
        val list = arrayListOf<Poster>()
        for ( i in 0 until 10) {
            list.add(Poster(i.toLong(), "Frozen II+${i}", "2019", "1 h 43 min", "Frozen II, also known as Frozen 2,", "King Agnarr of Arendelle tells a story to his young children.", "https://user-images.githubusercontent.com/24237865/75087937-5c1d9f80-553e-11ea-8fc9-a7e520addde0.jpg"))
        }
        return list
    }
}