package com.project.applemarket.data

import android.media.Image
import android.os.Parcelable
import androidx.annotation.DrawableRes
import com.project.applemarket.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Long,
    var nickname: String,
    var location: String,
    var manner: Float = 36.5f,
    val interests: MutableList<Item> = mutableListOf(),
    @DrawableRes var profileImage: Int = R.drawable.profile_default
) : Parcelable {

}