package com.example.composemvvm.ui.views

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
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
            Text(text = "back", modifier = Modifier
                .constrainAs(backView) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start, margin = 16.dp)
                }
                .onBounceClick {
                    onBack()
                })
        }
    }
}