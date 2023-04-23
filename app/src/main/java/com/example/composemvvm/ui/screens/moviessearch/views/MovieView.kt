package com.example.composemvvm.ui.screens.moviessearch.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.composemvvm.extentions.Background
import com.example.composemvvm.models.movies.Movie

@Composable
fun MovieView(movie: Movie, modifier: Modifier) {
    val viewWidth = (LocalConfiguration.current.screenWidthDp).dp / 2
    val viewHeight = viewWidth + 86.dp
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = modifier
            .padding(2.dp)
            .width(viewWidth)
            .height(viewHeight)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.Background)
    ) {

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(movie.poster)
                .crossfade(true)
                .build(),
            onState = {
//                isVisible.value = it is AsyncImagePainter.State.Success
            },
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .widthIn(max = (LocalConfiguration.current.screenWidthDp).dp - 32.dp)
                .height(viewHeight)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0x9A2653DA))
                .padding(4.dp)
        ) {
            val title = "${movie.title} (${movie.year})"
            Text(text = title, color = Color.White)
        }
    }
}