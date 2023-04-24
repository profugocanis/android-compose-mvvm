package com.example.composemvvm.ui.screens.shared

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.composemvvm.core.ui.BaseScreen
import com.example.composemvvm.extentions.onBounceClick
import com.mxalbert.sharedelements.FadeMode
import com.mxalbert.sharedelements.MaterialContainerTransformSpec
import com.mxalbert.sharedelements.SharedElementsRoot
import com.mxalbert.sharedelements.SharedMaterialContainer

const val url =
    "https://m.media-amazon.com/images/M/MV5BMjEwMzMxODIzOV5BMl5BanBnXkFtZTgwNzg3OTAzMDI@._V1_SX300.jpg"

object SharedScreen : BaseScreen() {

    @Composable
    fun Screen(nav: NavController) {

        var isShared by remember { mutableStateOf(false) }

        Column(modifier = Modifier.onBounceClick {
            isShared = !isShared
        }) {
            SharedElementsRoot {

                if (!isShared) {
                    SharedMaterialContainer(
                        key = "tag",
                        screenKey = "DetailsScreen2",
                        transitionSpec = FadeOutTransitionSpec
                    ) {
                        Image(100)
                    }
                }

                if (isShared) {
                    SharedMaterialContainer(
                        key = "tag",
                        screenKey = "DetailsScreen",
                        isFullscreen = true,
                        transitionSpec = FadeOutTransitionSpec
                    ) {
                        Image(300)
                    }
                }
            }
        }
    }

    @Composable
    private fun Image(sizeDp: Int) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(url)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .width(sizeDp.dp)
        )
    }
}


private val FadeOutTransitionSpec = MaterialContainerTransformSpec(
    durationMillis = 300,
    fadeMode = FadeMode.Out
)