package io.github.megasoheilsh.xray.dto

import android.graphics.drawable.Drawable

data class AppList(
    var appIcon: Drawable,
    var appName: String,
    var packageName: String,
)
