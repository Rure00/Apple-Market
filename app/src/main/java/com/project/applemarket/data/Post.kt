package com.project.applemarket.data

import android.media.Image
import java.time.LocalDateTime

data class Post(
    val id: Long,
    val item: Item,
    val user: User,

    var title: String,
    var body: String,
    var price: Int,

    val images: MutableList<Image> = mutableListOf(),
    val createAt: LocalDateTime = LocalDateTime.now()
) {
    var interest: Int = 0
    var chatNum: Int = 0
}