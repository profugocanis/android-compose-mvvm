package com.example.composemvvm.ui.dialogs

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.fragment.app.FragmentManager
import coil.compose.rememberAsyncImagePainter
import com.example.composemvvm.core.ui.BaseBottomDialog
import com.example.composemvvm.ui.views.ZoomableImage

class ImageViewerDialog : BaseBottomDialog() {

    private var url: String? = null

    @Composable
    override fun DialogContent() {
        ImageViewerView()
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun ImageViewerView() {
        ZoomableImage(
            painter = rememberAsyncImagePainter(url),
            isRotation = false,
            modifier = Modifier.fillMaxSize(),
            backgroundColor = Color.Black
        ) {
            isDraggable = it == 1f
        }
    }

    companion object {

        fun show(manager: FragmentManager, url: String?) {
            url ?: return
            val dialog = ImageViewerDialog()
            dialog.url = url
            dialog.isCancelable = true
            dialog.show(manager, dialog.tag)
        }
    }
}