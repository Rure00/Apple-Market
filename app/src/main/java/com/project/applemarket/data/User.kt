package com.project.applemarket.data

import android.media.Image
import androidx.annotation.DrawableRes
import com.project.applemarket.R

data class User(
    val id: Long,
    var nickname: String,
    var location: String,
    @DrawableRes var profileImage: Int = R.drawable.profile_default
) {
    var manner: Float = 36.5f
    val interests: MutableList<Item> = mutableListOf()
}