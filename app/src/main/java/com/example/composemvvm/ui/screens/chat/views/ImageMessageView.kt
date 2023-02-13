package com.example.composemvvm.ui.screens.chat.views

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.composemvvm.R
import com.example.composemvvm.extentions.CustomBlue
import com.example.composemvvm.models.Message
import com.example.composemvvm.models.MessageData
import com.example.composemvvm.ui.dialogs.ImageViewerDialog
import com.example.composemvvm.ui.screens.second.SecondScreen
import com.example.composemvvm.ui.views.CustomPopMenu
import com.example.composemvvm.ui.views.PopMenuItem

@Composable
fun ImageMessageView(message: Message, menuItems: List<PopMenuItem>) {
    val isInput = message.isInput
    val alignment = if (isInput) Alignment.TopStart else Alignment.TopEnd
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 4.dp),
        contentAlignment = alignment
    ) {
        CustomPopMenu(menuItems) { expanded ->
            ImageMessageContentView(message) { expanded.value = true }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageMessageContentView(message: Message, onLongClick: () -> Unit) {
    val url = message.getData<MessageData.Image>().url
    val isInput = message.isInput
    val radius = 16.dp
    val shape = if (isInput) {
        RoundedCornerShape(topStart = radius, topEnd = radius, bottomEnd = radius)
    } else {
        RoundedCornerShape(topStart = radius, topEnd = radius, bottomStart = radius)
    }
    val backgroundColor = if (isInput) Color.CustomBlue else Color.Gray
    val manager = (SecondScreen.getContext() as AppCompatActivity).supportFragmentManager
    Box(
        modifier = Modifier
            .clip(shape)
            .border(BorderStroke(1.dp, backgroundColor), shape)
            .combinedClickable(onClick = {
                ImageViewerDialog.show(manager, url)
            }, onLongClick = onLongClick)
            .background(backgroundColor)
            .padding(horizontal = 0.dp, vertical = 0.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        val screenWidthDp = LocalConfiguration.current.screenWidthDp.div(1.8)
        Image(
            painter = rememberAsyncImagePainter(url),
            contentDescription = null,
            modifier = Modifier
                .size(screenWidthDp.dp),
            contentScale = ContentScale.Crop
        )

        val padding = if (isInput) 8.dp else 4.dp
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = padding, vertical = 4.dp)
        ) {
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