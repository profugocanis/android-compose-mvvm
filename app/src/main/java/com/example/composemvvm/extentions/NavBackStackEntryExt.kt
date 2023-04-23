package com.example.composemvvm.extentions

import androidx.navigation.NavBackStackEntry
import com.google.gson.Gson

inline fun <reified T> NavBackStackEntry.getObject(key: String): T? {
    val json = arguments?.getString(key)
    return try {
        Gson().fromJson(json, T::class.java)
    } catch (_: Exception) {
        null
    }
}

fun NavBackStackEntry.getInt(key: String): Int? {
    return try {
        arguments?.getString(key)?.toInt()
    } catch (_: Exception) {
        null
    }
}

fun NavBackStackEntry.getString(key: String): String? {
    return try {
        arguments?.getString(key)
    } catch (_: Exception) {
        null
    }
}

fun NavBackStackEntry.getLong(key: String): Long? {
    return try {
        arguments?.getString(key)?.toLong()
    } catch (_: Exception) {
        null
    }
}