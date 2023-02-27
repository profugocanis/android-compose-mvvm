package com.example.composemvvm.ui.screens.chat.views

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.composemvvm.extentions.CustomBlue
import com.example.composemvvm.extentions.CustomGray
import com.example.composemvvm.models.Message
import com.example.composemvvm.models.MessageData
import com.example.composemvvm.ui.dialogs.ImageViewerDialog
import com.example.composemvvm.ui.views.CustomPopMenu
import com.example.composemvvm.ui.views.PopMenuItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MessageView(
    message: Message,
    menuItems: List<PopMenuItem>,
    modifier: Modifier,
    onReplayTap: (Message) -> Unit
) {

    val radius = 16.dp
    val isInput = message.isInput
    val shape = if (isInput) {
        RoundedCornerShape(topStart = radius, topEnd = radius, bottomEnd = radius)
    } else {
        RoundedCornerShape(topStart = radius, topEnd = radius, bottomStart = radius)
    }
    val alignment = if (isInput) Alignment.TopStart else Alignment.TopEnd
    val backgroundColor = if (isInput) Color.CustomGray else Color.CustomBlue
    val isVisible = remember { mutableStateOf(message.data !is MessageData.Image) }

    Box(
        modifier = modifier
            .alpha(if (isVisible.value) 1f else 0f)
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        contentAlignment = alignment
    ) {
        val manager = (LocalContext.current as AppCompatActivity).supportFragmentManager
        CustomPopMenu(
            menuItems,
            modifier = Modifier
                .clip(shape)
                .background(backgroundColor)
                .padding(1.dp)
        ) { expanded ->
            ConstraintLayout() {

                val (replayMessageView, messageView) = createRefs()

                if (message.replayedMessage != null) {
                    ReplayMessageView(
                        message.replayedMessage,
                        modifier = Modifier
                            .constrainAs(replayMessageView) {
                                top.linkTo(parent.top)
                                bottom.linkTo(replayMessageView.top)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                width = Dimension.fillToConstraints
                            }
                            .clickable { onReplayTap(message.replayedMessage) }
                            .padding(horizontal = 8.dp)
                            .padding(top = 4.dp)
                            .padding(bottom = if (message.data is MessageData.Image) 4.dp else 0.dp),
                        textColor = Color.White
                    )
                }

                val messageModifier = Modifier.constrainAs(messageView) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    if (message.replayedMessage != null) {
                        top.linkTo(replayMessageView.bottom)
                    } else {
                        top.linkTo(parent.top)
                    }
                    bottom.linkTo(parent.bottom)
                }
                when (message.data) {
                    is MessageData.Image -> {
                        val imageShape = if (message.replayedMessage == null) shape else RoundedCornerShape(0)
                        ImageMessageView(
                            message = message,
                            modifier = messageModifier
                                .clip(imageShape)
                                .combinedClickable(
                                    onClick = {
                                        val url = message.getData<MessageData.Image>()?.url
                                        ImageViewerDialog.show(manager, url)
                                    },
                                    onLongClick = { expanded.value = true }
                                ),
                            isVisible = isVisible
                        )
                    }
                    is MessageData.Text -> {
                        TextMessageView(
                            message = message,
                            modifier = messageModifier
                                .padding(horizontal = 4.dp, vertical = 4.dp)
                                .combinedClickable(
                                    onClick = { },
                                    onLongClick = { expanded.value = true }
                                ))
                    }
                    null -> {}
                }
            }
        }
    }
}