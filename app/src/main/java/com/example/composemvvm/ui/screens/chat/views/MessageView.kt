package com.example.composemvvm.ui.screens.chat.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composemvvm.R
import com.example.composemvvm.extentions.CustomBlue
import com.example.composemvvm.models.Message
import com.example.composemvvm.ui.views.CustomPopMenu
import com.example.composemvvm.ui.views.PopMenuItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MessageView(
    message: Message,
    isInput: Boolean,
    modifier: Modifier,
    menuItems: List<PopMenuItem>
) {
    val radius = 16.dp
    val shape = if (isInput) {
        RoundedCornerShape(topStart = radius, topEnd = radius, bottomEnd = radius)
    } else {
        RoundedCornerShape(topStart = radius, topEnd = radius, bottomStart = radius)
    }

    val alignment = if (isInput) Alignment.TopStart else Alignment.TopEnd
    val backgroundColor = if (isInput) Color.CustomBlue else Color.Gray

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 4.dp),
        contentAlignment = alignment
    ) {
        CustomPopMenu(menuItems) { expanded ->
            Row(
                modifier = Modifier
                    .clip(shape)
                    .combinedClickable(
                        onClick = { },
                        onLongClick = { expanded.value = true }
                    )
                    .background(backgroundColor)
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

                if (!isInput) {
                    val id = if (message.isSend) R.drawable.ic_check else R.drawable.ic_watch
                    Icon(
                        painter = painterResource(id = id),
                        contentDescription = "",
                        modifier = Modifier.size(14.dp),
                        tint = Color.White
                    )
                }
            }
        }
    }
}