package com.example.composemvvm.extentions

import androidx.navigation.NavBackStackEntry
import com.google.gson.Gson

inline fun <reified T> NavBackStackEntry.getObject(key: String): T {
    val json = arguments?.getString(key)
    return Gson().fromJson(json, T::class.java)
}

fun NavBackStackEntry.getInt(key: String): Int? {
    return arguments?.getString(key)?.toInt()
}

fun NavBackStackEntry.getString(key: String): String? {
    return arguments?.getString(key)
}

fun NavBackStackEntry.getLong(key: String): Long? {
    return arguments?.getString(key)?.toLong()
}