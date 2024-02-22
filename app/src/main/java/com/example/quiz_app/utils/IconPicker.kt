package com.example.quiz_app.utils

import com.example.quiz_app.R

object IconPicker {
    val icons = arrayOf(
       R.drawable.icon_i,
       R.drawable.icon_ii,
       R.drawable.icon_iii,
       R.drawable.icon_iv,
       R.drawable.icon_v,
       R.drawable.icon_vi,
       R.drawable.icon_vii,
       R.drawable.icon_viii,

    )
    var currentIcon = 0

    fun getIcon(): Int {
        currentIcon = (currentIcon + 1) % icons.size
        return icons[currentIcon]
    }
}