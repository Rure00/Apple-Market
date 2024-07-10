package com.project.applemarket.data

import android.media.Image
import androidx.annotation.DrawableRes

data class Item(
    val id: Long,
    var name: String,
    @DrawableRes var image: Int
)