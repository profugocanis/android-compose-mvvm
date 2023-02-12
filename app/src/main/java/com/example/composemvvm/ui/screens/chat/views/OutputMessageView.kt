package com.example.composemvvm.ui.screens.chat.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composemvvm.models.Message

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OutputMessageView(message: Message, onLongClick: () -> Unit) {
    val radius = 16.dp
    val shape = RoundedCornerShape(topStart = radius, topEnd = radius, bottomStart = radius)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 4.dp),
        contentAlignment = Alignment.TopEnd
    ) {
        Row(
            modifier = Modifier
                .clip(shape)
                .combinedClickable(
                    onClick = { },
                    onLongClick = onLongClick
                )
                .background(Color.Gray)
                .padding(horizontal = 4.dp, vertical = 8.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = message.text.toString(),
                color = Color.White,
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 8.dp)
            )
            Text(
                text = "13:32",
                color = Color.White,
                fontSize = 12.sp,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }
    }
}