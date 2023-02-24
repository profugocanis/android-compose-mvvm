package com.example.composemvvm.ui.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp

class PopMenuItem(val title: String, val color: Color = Color.Black, val onClick: () -> Unit)

@Composable
fun CustomPopMenu(
    menuItems: List<PopMenuItem>,
    content: @Composable BoxScope.(MutableState<Boolean>) -> Unit
) {
    val expanded = remember { mutableStateOf(false) }
    Box {
        content(expanded)
        DropdownMenu(
            expanded = expanded.value,
            offset = DpOffset(16.dp, (-8).dp),
            onDismissRequest = { expanded.value = false }) {
            menuItems.forEach {
                DropdownMenuItem(onClick = {
                    it.onClick()
                    expanded.value = false
                }) {
                    Text(text = it.title, color = it.color)
                }
            }
        }
    }
}