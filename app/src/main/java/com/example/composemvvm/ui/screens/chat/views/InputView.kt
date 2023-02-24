package com.example.composemvvm.ui.screens.chat.views

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.composemvvm.R
import com.example.composemvvm.extentions.CustomBlue
import com.example.composemvvm.extentions.CustomLightGray
import com.example.composemvvm.extentions.onBounceClick
import com.example.composemvvm.models.MessageData
import com.example.composemvvm.ui.activities.MainActivity
import com.example.composemvvm.ui.screens.chat.ChatScreen
import com.example.composemvvm.ui.screens.chat.ChatScreenState
import com.example.composemvvm.ui.screens.chat.ChatViewModel
import com.example.composemvvm.utils.KeyboardManager

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun InputView(
    viewModel: ChatViewModel,
    screenState: ChatScreenState,
    modifier: Modifier
) {
    val text = remember { mutableStateOf(TextFieldValue("")) }
    Column(modifier = modifier.fillMaxWidth()) {

        Divider()

        if (screenState.replayMessage.value != null) {
            ReplayMessage(screenState)
        }

        TextField(
            value = text.value,
            maxLines = 3,
            placeholder = { Text(text = "Enter text") },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            onValueChange = { text.value = it },
            trailingIcon = {
                Icon(
                    Icons.Filled.Send,
                    contentDescription = null,
                    modifier = Modifier.onBounceClick {
                        ChatScreen.sendMessage(text.value.text.trim(), screenState, viewModel)
                        text.value = TextFieldValue("")
                    })
            },
            leadingIcon = {
                val activity = ChatScreen.getActivity() as? MainActivity
                Icon(painter = painterResource(id = R.drawable.ic_image),
                    contentDescription = null,
                    modifier = Modifier.onBounceClick {
                        KeyboardManager.hideKeyBoard(activity)
                        activity?.imageHelper?.select {
                            ChatScreen.sendImage(it, screenState, viewModel)
                        }
                    })
            },
            shape = CircleShape,
            colors = TextFieldDefaults.textFieldColors(
                leadingIconColor = Color.CustomBlue,
                trailingIconColor = Color.CustomBlue,
                backgroundColor = Color.CustomLightGray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )
    }
}

@Composable
private fun ReplayMessage(screenState: ChatScreenState) {
    Row(modifier = Modifier.padding(horizontal = 4.dp)) {
        Text(text = "Replay")
        when (val messageData = screenState.replayMessage.value?.data) {
            is MessageData.Text -> {
                Text(
                    text = messageData.text ?: "",
                    modifier = Modifier.widthIn(max = 200.dp),
                    maxLines = 2
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
                    modifier = Modifier.size(56.dp)
                )
            }
            else -> {}
        }

        Icon(
            Icons.Filled.Close,
            contentDescription = null,
            modifier = Modifier.onBounceClick {
                screenState.replayMessage.value = null
            })
    }
}