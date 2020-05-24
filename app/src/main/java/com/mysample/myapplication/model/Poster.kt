package com.mysample.disneymotions.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Poster(
    @PrimaryKey val id: Long,
    val name: String,
    val release: String,
    val playtime: String,
    val description: String,
    val plot: String,
    val poster: String
) : Parcelable