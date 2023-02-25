package com.example.composemvvm.ui.screens.chat.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composemvvm.R
import com.example.composemvvm.models.Message
import com.example.composemvvm.models.MessageData
import com.example.composemvvm.ui.views.CustomPopMenu
import com.example.composemvvm.ui.views.PopMenuItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TextMessageView(
    message: Message,
    modifier: Modifier,
    menuItems: List<PopMenuItem>
) {
    val isInput = message.isInput
    CustomPopMenu(menuItems) { expanded ->
        Row(
            modifier = modifier
                .combinedClickable(onClick = { }, onLongClick = { expanded.value = true })
                .padding(horizontal = 4.dp, vertical = 8.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            val screenWidthDp = LocalConfiguration.current.screenWidthDp.div(1.5)
            Text(
                text = message.getData<MessageData.Text>().text.toString(),
                color = Color.White,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .widthIn(max = screenWidthDp.dp)
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