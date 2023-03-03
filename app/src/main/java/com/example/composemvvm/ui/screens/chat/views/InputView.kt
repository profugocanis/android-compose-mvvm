package com.example.composemvvm.ui.screens.chat.views

import androidx.compose.animation.*
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.composemvvm.R
import com.example.composemvvm.extentions.CustomBlue
import com.example.composemvvm.extentions.CustomLightGray
import com.example.composemvvm.extentions.isRtl
import com.example.composemvvm.extentions.onBounceClick
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

        AnimatedVisibility(
            visible = screenState.replayMessage.value != null,
            enter = expandVertically(
                spring(
                    stiffness = Spring.StiffnessLow,
                    visibilityThreshold = IntSize.VisibilityThreshold
                ),
            ),
            exit = shrinkVertically(),
        ) {
            ReplayMessageInputView(
                screenState.replayMessage.value,
                modifier = Modifier.fillMaxWidth(),
                onClear = {
                    screenState.replayMessage.value = null
                })
        }

        TextField(
            value = text.value,
            maxLines = 3,
            placeholder = { Text(text = stringResource(R.string.enter_text)) },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            onValueChange = { text.value = it },
            trailingIcon = {
                Icon(
                    Icons.Filled.Send,
                    contentDescription = null,
                    modifier = Modifier
                        .scale(scaleX = if (LocalContext.current.isRtl) -1f else 1f, scaleY = 1f)
                        .onBounceClick {
                            ChatScreen.sendMessage(text.value.text.trim(), screenState, viewModel)
                            text.value = TextFieldValue("")
                        })
            },
            leadingIcon = {
                val activity = ChatScreen.getActivity() as? MainActivity
                Icon(painter = painterResource(id = R.drawable.ic_image),
                    contentDescription = null,
                    modifier = Modifier
                        .onBounceClick {
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