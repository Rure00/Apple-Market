package com.project.applemarket.data

import android.media.Image
import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class Item(
    val id: Long,
    var name: String,
    @DrawableRes var image: Int
) : Parcelable