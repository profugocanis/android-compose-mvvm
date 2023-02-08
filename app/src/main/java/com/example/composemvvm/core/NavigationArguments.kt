package com.example.composemvvm.core

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

class NavigationArguments {

    private val arguments = mutableListOf<NamedNavArgument>()

    fun registerString(key: String): NavigationArguments {
        arguments.add(
            navArgument(key) {
                type = NavType.StringType
                defaultValue = ""
            }
        )
        return this
    }

    fun getBaseUrl(): String {
        var result = ""
        arguments.forEach {
            val name = it.name
            result += "$name={$name}&"
        }
        return result
    }

    fun getNavigationArguments() = arguments

    companion object {

        fun createParameters(args: Map<String, String> = mapOf()): String {
            var parameters = ""
            args.forEach {
                parameters += "${it.key}=${it.value}&"
            }
            return parameters
        }
    }
}