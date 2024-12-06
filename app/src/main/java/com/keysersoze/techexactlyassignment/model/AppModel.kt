package com.keysersoze.techexactlyassignment.model

import android.graphics.drawable.Drawable

data class AppModel(
    val name: String,
    val packageName: String,
    val icon: Drawable,
    var isEnabled: Boolean
)