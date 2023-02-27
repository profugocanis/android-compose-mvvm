package com.example.composemvvm.core

import android.content.Context
import android.content.ContextWrapper
import android.content.res.Resources
import android.os.Build
import android.os.LocaleList
import org.intellij.lang.annotations.Language
import java.util.*

object LanguageHelper {

    var languages = listOf<Language>()
    var resources: Resources? = null
        private set

//    fun setLanguage(language: Language): Boolean {
//        val currentCode = settingStorage.getLanguageCode()
//        if (currentCode == language.locale.language) return true
//        settingStorage.saveLanguageCode(language.locale.language)
//        updateLocale()
//        return false
//    }

//    fun getCurrentLanguageShort(): String {
//        val language = Locale.getDefault().language
//        if (language == "iw") return "he"
//        return language
//    }
//
//    fun updateLocale() {
//        val locale: Locale = getCurrentLocale()
//        val resources: Resources = MedtrixApp.application?.resources ?: return
//        val configuration = resources.configuration ?: return
//        configuration.setLocale(locale)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            MedtrixApp.application?.createAttributionContext(configuration.toString())
//        } else {
//            resources.updateConfiguration(configuration, resources.displayMetrics)
//        }
//    }

//    fun getLanguageCurrentPosition(): Int {
//        val currentCode = getCurrentCode()
//        return languages.indexOfFirst { it.locale.language == currentCode }
//    }
//
//    fun getCurrentName(): String {
//        return Locale.getDefault().displayLanguage
//    }

    private var localeToSwitchTo = Locale.ENGLISH
    var language: Locale
        get() = localeToSwitchTo
        set(value) {
            localeToSwitchTo = value
        }

    fun configureContextForLanguage(newBase: Context): ContextWrapper {
//        val localeToSwitchTo = Locale("iw")
//        val localeToSwitchTo = Locale.ENGLISH
        val resources: Resources = newBase.resources
        val configuration = resources.configuration
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val localeList = LocaleList(localeToSwitchTo)
            LocaleList.setDefault(localeList)
            configuration.setLocales(localeList)
        } else {
            configuration.locale = localeToSwitchTo
        }
        newBase.createConfigurationContext(configuration)
        this.resources = Resources(newBase.assets, resources.displayMetrics, configuration)
        return ContextWrapper(newBase)
    }

//    private fun getCurrentLocale(): Locale {
//        return this.getCurrentLanguage().locale
//    }

//    private fun getCurrentLanguage(): Language {
//        return Language
//        val currentCode = getCurrentCode()
//        var currentLanguage = languages.firstOrNull { it.locale.language == currentCode }
//        if (currentLanguage == null) {
//            currentLanguage = languages.first()
//            setLanguage(currentLanguage)
//        }
//        return currentLanguage
//    }

//    private fun getCurrentCode(): String {
//        var currentCode = settingStorage.getLanguageCode()
//        if (currentCode.isEmpty()) {
//            currentCode = Locale.getDefault().language
//        }
//        return currentCode
//    }
//
//    data class Language(
//        val locale: Locale,
//        @DrawableRes val icon: Int? = null,
//    ) {
//        val name: String get() = locale.displayLanguage
//    }
}