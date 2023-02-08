package com.example.composemvvm.extentions

import androidx.navigation.NavBackStackEntry
import com.google.gson.Gson

inline fun <reified T> NavBackStackEntry.getObject(key: String): T {
    val json = arguments?.getString(key)
    return Gson().fromJson(json, T::class.java)
}