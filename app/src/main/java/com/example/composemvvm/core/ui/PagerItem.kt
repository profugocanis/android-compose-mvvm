package com.example.composemvvm.core.ui

import androidx.compose.runtime.Composable

class PagerItem(val title: String) {

    @Composable
    fun Page(view: @Composable (PagerItem) -> Unit) {
        view(this)
    }
}