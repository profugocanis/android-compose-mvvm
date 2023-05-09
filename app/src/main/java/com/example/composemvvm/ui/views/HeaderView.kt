package com.example.composemvvm.ui.views

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.composemvvm.extentions.isRtl
import com.example.composemvvm.extentions.onBounceClick

@Composable
fun HeaderView(title: String, onBack: (() -> Unit)? = null) {
    ConstraintLayout(
        modifier = Modifier
            .height(40.dp)
            .fillMaxWidth()
    ) {

        val (titleView, backView) = createRefs()

        Text(text = title, modifier = Modifier.constrainAs(titleView) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        })

        if (onBack != null) {
            val context = LocalContext.current
            Icon(
                Icons.Outlined.ArrowBack,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier
                    .padding(end = 2.dp)
                    .constrainAs(backView) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start, margin = 16.dp)
                    }
                    .scale(scaleX = if (context.isRtl) -1f else 1f, scaleY = 1f)
                    .onBounceClick {
                        onBack()
                    }
            )
        }
    }
}