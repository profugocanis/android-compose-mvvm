package com.example.composemvvm.ui.screens.chat.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.composemvvm.extentions.CustomBlue
import com.example.composemvvm.extentions.onBounceClick
import com.example.composemvvm.models.Message
import com.example.composemvvm.models.MessageData

@Composable
fun ReplayMessageView(
    message: Message?,
    modifier: Modifier,
    textColor: Color = Color.Black
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {

        Divider(
            color = Color.White,
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .height(if (message?.data is MessageData.Image) 32.dp else 16.dp)
                .width(1.dp)
        )

        when (val messageData = message?.data) {
            is MessageData.Text -> {
                Text(
                    text = messageData.text ?: "",
                    modifier = Modifier,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = textColor,
                    fontSize = 13.sp
                )
            }
            is MessageData.Image -> {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(messageData.getImageData())
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(44.dp)
                        .clipToBounds()
                )
            }
            else -> {}
        }
    }
}

@Composable
fun ReplayMessageInput(
    message: Message?,
    modifier: Modifier,
    textColor: Color = Color.Black,
    onClear: (() -> Unit)? = null
) {
    ConstraintLayout(modifier = modifier) {

        val (titleView, messageView, closeIcon) = createRefs()

        if (onClear != null) {
            Text(
                text = "Replay",
                color = Color.CustomBlue,
                modifier = Modifier.constrainAs(titleView) {
                    start.linkTo(parent.start, margin = 8.dp)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                })
        } else {
            Divider(color = Color.White, modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
                .constrainAs(titleView) {
                    start.linkTo(parent.start, margin = 4.dp)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    height = Dimension.fillToConstraints
                })
        }

        val messageModifier = Modifier.constrainAs(messageView) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            if (onClear != null) {
                start.linkTo(titleView.end, margin = 8.dp)
                if (message?.data is MessageData.Text) {
                    end.linkTo(closeIcon.start)
                    width = Dimension.fillToConstraints
                }
            } else {
                start.linkTo(parent.start, margin = 32.dp)
                end.linkTo(parent.end, margin = 8.dp)
//                width = Dimension.fillToConstraints
            }
        }
        when (val messageData = message?.data) {
            is MessageData.Text -> {
                Text(
                    text = messageData.text ?: "",
                    modifier = messageModifier,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = textColor,
                    fontSize = 14.sp
                )
            }
            is MessageData.Image -> {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(messageData.getImageData())
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = messageModifier.size(44.dp)
                )
            }
            else -> {}
        }

        if (onClear != null) {
            Icon(
                Icons.Filled.Close,
                contentDescription = null,
                modifier = Modifier
                    .constrainAs(closeIcon) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                    .padding(8.dp)
                    .onBounceClick {
                        onClear.invoke()
                    })
        }
    }
}