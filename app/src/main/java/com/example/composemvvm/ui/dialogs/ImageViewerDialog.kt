package com.example.composemvvm.ui.dialogs

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.fragment.app.FragmentManager
import coil.compose.rememberAsyncImagePainter
import com.example.composemvvm.core.ui.BaseBottomDialog

class ImageViewerDialog : BaseBottomDialog() {

    private var url: String? = null

    @Composable
    override fun DialogContent() {
        val isShowBackground = remember { mutableStateOf(false) }
        setOnSlideOffset {
            isShowBackground.value = it == 1f
        }
        ImageViewerView(isShowBackground)
    }

    @Composable
    private fun ImageViewerView(isShowBackground: MutableState<Boolean>) {
        Box {
//            AnimatedVisibility(
//                visible = isShowBackground.value,
//                enter = fadeIn(animationSpec = tween(durationMillis = 50)),
//                exit = fadeOut(animationSpec = tween(durationMillis = 50)),
//            ) {
//                Spacer(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .background(Color.Black)
//                )
//            }
            Image(
                painter = rememberAsyncImagePainter(url),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
//            contentScale = ContentScale.Crop
            )
        }
    }

    companion object {

        fun show(manager: FragmentManager, url: String?, context: Context) {
            val dialog = ImageViewerDialog()
            dialog.url = url
            dialog.isCancelable = true
            dialog.show(manager, dialog.tag)
        }
    }
}