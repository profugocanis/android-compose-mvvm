package com.example.composemvvm.ui.screens.third

import android.app.Activity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.composemvvm.R
import com.example.composemvvm.core.LanguageHelper
import com.example.composemvvm.core.ui.BaseScreen
import com.example.composemvvm.extentions.Background
import com.example.composemvvm.extentions.CustomLightGray
import com.example.composemvvm.logget
import com.example.composemvvm.ui.activities.MainActivity
import com.example.composemvvm.ui.screens.chat.ChatScreen
import com.example.composemvvm.ui.screens.first.FirstPageScreen
import com.example.composemvvm.ui.screens.fourth.FourthScreen
import java.util.*

object ThirdScreen : BaseScreen() {

    fun open(nav: NavController) {
        navigate(nav)
    }

    @OptIn(ExperimentalAnimationApi::class)
    @Composable
    fun Screen(nav: NavController) {

        onDestroy {
            logget("ThirdScreen onDestroy")
        }

        val radius = 32.dp
        val shape = RoundedCornerShape(topStart = radius, bottomEnd = radius)

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.CustomLightGray)
        ) {

            BackgroundView()

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(72.dp)
                    .border(0.5.dp, color = Color.CustomLightGray, shape)
            ) {
                Column(
                    modifier = Modifier
                        .clip(shape)
                        .background(Color.Background)
                        .fillMaxSize()
                ) {}

                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {

                    Text(text = stringResource(R.string.examples))

                    GradientButton(
                        listOf(Color(0xFF3361EA), Color(0xFF6285EC)),
                        16.dp,
                        stringResource(R.string.list)
                    ) {
                        FirstPageScreen.open(nav)
                    }
                    GradientButton(
                        listOf(Color(0xFF6285EC), Color(0xFF3361EA)),
                        16.dp,
                        stringResource(R.string.chat)
                    ) {
                        ChatScreen.open(nav)
                    }

                    GradientButton(
                        listOf(Color(0xFF6285EC), Color(0xFF3361EA)),
                        16.dp,
                        "Fourth Screen"
                    ) {
                        FourthScreen.open(nav)
                    }

                    Spacer(modifier = Modifier.fillMaxHeight(0.9f))

                    val activity = LocalContext.current as Activity
                    val currentLanguage = LanguageHelper.language
                    GradientButton(
                        listOf(Color(0xFF6285EC), Color(0xFF3361EA)),
                        16.dp,
                        currentLanguage.language
                    ) {

                        if (currentLanguage == Locale.ENGLISH) {
                            LanguageHelper.language = Locale("iw")
                        } else {
                            LanguageHelper.language = Locale.ENGLISH
                        }
                        MainActivity.refresh(activity)
                    }
                }
            }
        }
    }
}

@Composable
private fun BackgroundView() {
    Canvas(modifier = Modifier.fillMaxSize(1.0f)) {
        drawCircle(
            brush = Brush.linearGradient(
                colors = listOf(Color(0xFF7A26D9), Color(0xFFE444E1)),
                start = Offset(450.dp.toPx(), 60.dp.toPx()),
                end = Offset(290.dp.toPx(), 190.dp.toPx()),
                tileMode = TileMode.Clamp
            ),
            center = Offset(375.dp.toPx(), 125.dp.toPx()),
            radius = 100.dp.toPx()
        )
        drawCircle(
            color = Color(0xFFEA357C),
            center = Offset(100.dp.toPx(), 265.dp.toPx()),
            radius = 55.dp.toPx()
        )
        drawCircle(
//            color = Color(0xFFC05AF7),
            brush = Brush.linearGradient(
                colors = listOf(Color(0xFF263ED9), Color(0xFFC05AF7)),
                start = Offset(50.dp.toPx(), 60.dp.toPx()),
                end = Offset(290.dp.toPx(), 190.dp.toPx()),
                tileMode = TileMode.Clamp
            ),
            center = Offset(50.dp.toPx(), 565.dp.toPx()),
            radius = 100.dp.toPx()
        )
        drawCircle(
            brush = Brush.linearGradient(
                colors = listOf(Color(0xFFEA334C), Color(0xFFEC6051)),
                start = Offset(180.dp.toPx(), 125.dp.toPx()),
                end = Offset(230.dp.toPx(), 125.dp.toPx()),
                tileMode = TileMode.Clamp
            ),
            center = Offset(205.dp.toPx(), 55.dp.toPx()),
            radius = 25.dp.toPx()
        )
    }
}

@Composable
private fun GradientButton(
    gradientColors: List<Color>,
    cornerRadius: Dp,
    title: String,
    onClick: () -> Unit
) {

    Button(
        modifier = Modifier
            .fillMaxWidth(),
//            .padding(start = 32.dp, end = 32.dp),
        onClick = onClick,
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent
        ),
        shape = RoundedCornerShape(topStart = cornerRadius, bottomEnd = cornerRadius)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(colors = gradientColors),
                    shape = RoundedCornerShape(topStart = cornerRadius, bottomEnd = cornerRadius)
                )
                .padding(horizontal = 16.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = title,
                color = Color.White
            )
        }
    }
}