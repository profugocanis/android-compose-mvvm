package com.example.composemvvm.ui.screens.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composemvvm.extentions.CustomBlue
import com.example.composemvvm.models.Message

@Composable
fun InputMessageView(message: Message) {
    val radius = 16.dp
    val shape = RoundedCornerShape(topStart = radius, topEnd = radius, bottomEnd = radius)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .clip(shape)
                .background(Color.CustomBlue)
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