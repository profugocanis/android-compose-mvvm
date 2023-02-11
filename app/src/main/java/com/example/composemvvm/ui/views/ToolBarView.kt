package com.example.composemvvm.ui.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.composemvvm.extentions.CustomLightGray

@Composable
fun ToolBarView(nav: NavHostController, startRoute: String) {
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
                    visible = isFirstScreen, enter = fadeIn(), exit = fadeOut(), modifier = Modifier
                ) {
                    Icon(
                        Icons.Filled.KeyboardArrowLeft,
                        "menu",
                        modifier = Modifier
                            .padding(12.dp)
                            .size(32.dp)
                            .clickable {
                                if (nav.previousBackStackEntry != null) {
                                    nav.popBackStack()
                                }
                            },
                        tint = Color.Blue
                    )
                }
            }

            Text(
                text = "${stackEntry?.destination?.displayName}",
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 32.dp)
            )
        }
        Divider()
    }
}