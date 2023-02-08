package com.example.composemvvm.extentions

import android.content.Context
import android.widget.Toast

fun Context.showInfo(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}