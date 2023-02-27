package com.example.composemvvm.ui.screens.chat.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.request.ImageRequest
import com.example.composemvvm.R
import com.example.composemvvm.models.Message
import com.example.composemvvm.models.MessageData

@Composable
fun ImageMessageView(
    message: Message,
    modifier: Modifier,
    isVisible: MutableState<Boolean>
) {
    val isInput = message.isInput
    Box(
        modifier = modifier,
        contentAlignment = Alignment.BottomEnd
    ) {
        val screenWidthDp = LocalConfiguration.current.screenWidthDp.div(1.8)

        val imageData = message.getData<MessageData.Image>()

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageData?.getImageData())
                .crossfade(true)
                .build(),
            onState = {
                isVisible.value = it is AsyncImagePainter.State.Success
            },
            contentDescription = null,
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .height(screenWidthDp.dp)
                .widthIn(max = (LocalConfiguration.current.screenWidthDp).dp - 32.dp)
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