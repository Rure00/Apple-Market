package com.project.applemarket.data

import android.media.Image
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class Post(
    val id: Long,
    val item: Item,
    val user: User,

    var title: String,
    var body: String,
    var price: Int,

    var interest: Int = 0,
    var chatNum: Int = 0
) : Parcelable {

}