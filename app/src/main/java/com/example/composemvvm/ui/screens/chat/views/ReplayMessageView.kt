package com.example.composemvvm.ui.screens.chat.views

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.composemvvm.extentions.onBounceClick
import com.example.composemvvm.models.Message
import com.example.composemvvm.models.MessageData

@Composable
fun ReplayMessageView(
    message: Message?,
    modifier: Modifier,
    textColor: Color = Color.Black,
    onClear: (() -> Unit)? = null
) {
    ConstraintLayout(
        modifier = modifier
            .padding(top = 4.dp)
    ) {

        val (titleView, messageView, closeIcon) = createRefs()

        if (onClear != null) {
            Text(
                text = "Replay",
                color = Color.Gray,
                modifier = Modifier.constrainAs(titleView) {
                    start.linkTo(parent.start, margin = 8.dp)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                })
        }

        val messageModifier = Modifier.constrainAs(messageView) {
            start.linkTo(titleView.end, margin = 8.dp)
            if (message?.data is MessageData.Text) {
                end.linkTo(closeIcon.start)
            }
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            width = Dimension.fillToConstraints
        }
        when (val messageData = message?.data) {
            is MessageData.Text -> {
                Text(
                    text = messageData.text ?: "",
                    modifier = messageModifier,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = textColor
                )
            }
            is MessageData.Image -> {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(messageData.getImageData())
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.FillHeight,
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