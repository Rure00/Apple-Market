package com.project.applemarket.data

import android.Manifest
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi


object Permission {
    const val NOTIFICATION_CODE = 100

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    val notification = arrayOf(
        Manifest.permission.POST_NOTIFICATIONS,
    )
}