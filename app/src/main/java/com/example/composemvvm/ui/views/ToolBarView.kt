package com.example.composemvvm.ui.views

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.composemvvm.extentions.CustomBlue
import com.example.composemvvm.extentions.CustomLightGray
import com.example.composemvvm.extentions.isRtl
import com.example.composemvvm.extentions.onBounceClick

@SuppressLint("SuspiciousIndentation")
@Composable
fun ToolBarView(nav: NavHostController, startRoute: String, title: String?) {
    val stackEntry = nav.currentBackStackEntryFlow.collectAsState(initial = null).value

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Box(
            contentAlignment = Alignment.Center, modifier = Modifier
                .height(56.dp)
                .background(Color.CustomLightGray)
        ) {
            val isFirstScreen =
                stackEntry?.destination != null && stackEntry.destination.route != startRoute

            Box(
                modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterStart
            ) {
                this@Column.AnimatedVisibility(
                    visible = isFirstScreen,
                    enter = fadeIn(),
                    exit = fadeOut(),
                    modifier = Modifier
                ) {
                    Icon(
                        Icons.Filled.KeyboardArrowLeft,
                        "menu",
                        modifier = Modifier
                            .padding(12.dp)
                            .size(32.dp)
                            .scale(scaleX = if (LocalContext.current.isRtl) -1f else 1f, scaleY = 1f)
                            .onBounceClick {
                                if (nav.previousBackStackEntry != null) {
                                    nav.popBackStack()
                                }
                            },
                        tint = Color.CustomBlue
                    )
                }
            }

            Text(
                text = title ?: "",
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
            )
        }
        Divider()
    }
}