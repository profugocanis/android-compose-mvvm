package com.example.composemvvm.core

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.composemvvm.logget
import com.google.gson.Gson

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


    fun createParameters(args: Map<String, Any> = mapOf()): String {
        var parameters = ""

        val convertedArgs = convertMap(args)
        logget(convertedArgs)

        val notFilledParam = arguments.firstOrNull { !convertedArgs.keys.contains(it.name) }
        if (notFilledParam != null) {
            throw Exception("Not added param: ${notFilledParam.name}")
        }

        val notFilledArg =
            convertedArgs.keys.firstOrNull { key -> !arguments.map { it.name }.contains(key) }
        if (notFilledArg != null) {
            throw Exception("Not register param: $notFilledArg")
        }

        convertedArgs.forEach {
            parameters += "${it.key}=${it.value}&"
        }
        return parameters
    }

    private fun convertMap(args: Map<String, Any>): Map<String, String> {
        return args.mapValues {
            if (it.value !is String) {
                return@mapValues Gson().toJson(it.value)
            }
            return@mapValues it.value as String
        }
    }
}