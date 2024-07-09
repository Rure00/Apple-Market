package com.project.applemarket.data

import android.media.Image

data class User(
    val id: Long,
    var nickname: String,
    var profileImage: Image,
    var location: String,
) {
    var manner: Float = 36.5f
    val interests: MutableList<Item> = mutableListOf()
}