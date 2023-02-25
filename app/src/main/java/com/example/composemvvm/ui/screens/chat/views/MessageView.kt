package com.example.composemvvm.ui.screens.chat.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.composemvvm.extentions.CustomBlue
import com.example.composemvvm.models.Message
import com.example.composemvvm.models.MessageData
import com.example.composemvvm.ui.views.PopMenuItem

@Composable
fun MessageView(message: Message, menuItems: List<PopMenuItem>, modifier: Modifier) {

    val radius = 16.dp
    val isInput = message.isInput
    val shape = if (isInput) {
        RoundedCornerShape(topStart = radius, topEnd = radius, bottomEnd = radius)
    } else {
        RoundedCornerShape(topStart = radius, topEnd = radius, bottomStart = radius)
    }
    val alignment = if (isInput) Alignment.TopStart else Alignment.TopEnd
    val backgroundColor = if (isInput) Color.CustomBlue else Color.Gray
    val isVisible = remember { mutableStateOf(message.data !is MessageData.Image) }

    Box(
        modifier = modifier
            .alpha(if (isVisible.value) 1f else 0f)
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 8.dp),
        contentAlignment = alignment
    ) {
        Column(
            modifier = Modifier
                .clip(shape)
                .background(backgroundColor)
                .padding(1.dp)
        ) {
            if (message.replayedMessage != null) {
                ReplayMessageView(
                    message.replayedMessage,
                    modifier = Modifier
                        .widthIn(max = 200.dp)
                        .padding(horizontal = 8.dp)
                        .padding(top = 4.dp)
                        .background(Color.Gray),
                    textColor = Color.White
                )
            }

            when (message.data) {
                is MessageData.Image -> {
                    ImageMessageView(
                        message = message,
                        modifier = Modifier.clip(shape),
                        menuItems = menuItems,
                        isVisible = isVisible
                    )
                }
                is MessageData.Text -> {
                    TextMessageView(
                        message = message,
                        modifier = Modifier,
                        menuItems = menuItems
                    )
                }
                null -> {}
            }
        }
    }
}