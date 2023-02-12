package com.example.composemvvm.ui.screens.chat.views

import android.view.Gravity
import android.view.View
import android.widget.PopupMenu
import androidx.annotation.StringRes

class ContextMenuBuilder(private val targetView: View) {
    private val mapMenuItem = mutableMapOf<String, () -> Unit>()

    fun addMenuItem(text: String, callback: () -> Unit): ContextMenuBuilder {
        mapMenuItem[text] = callback
        return this
    }

    fun addMenuItem(@StringRes res: Int, callback: () -> Unit): ContextMenuBuilder {
        mapMenuItem[targetView.context.getString(res)] = callback
        return this
    }

    fun create(): PopupMenu {
        return PopupMenu(targetView.context, targetView, Gravity.CENTER_HORIZONTAL).apply {
            var itemId = 100
            val itemIdCallbackMap = mutableMapOf<Int, () -> Unit>()
            mapMenuItem.forEach {
                menu.add(0, itemId, 0, it.key)
                itemIdCallbackMap[itemId] = it.value
                itemId++
            }

            setOnMenuItemClickListener {
                itemIdCallbackMap[it.itemId]?.invoke()
                return@setOnMenuItemClickListener false
            }
        }
    }
}