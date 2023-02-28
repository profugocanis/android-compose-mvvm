package com.example.composemvvm.ui.screens.first

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.composemvvm.extentions.shimmerBackground
import com.example.composemvvm.models.Product

val productMock = (0..16).map { Product() }

@Composable
fun ProductView(product: Product, modifier: Modifier) {
    if (product.isNull) {
        Box(
            modifier = modifier
                .height(64.dp)
                .shimmerBackground(RoundedCornerShape(4.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(156.dp)
                    .padding(18.dp)
                    .shimmerBackground(RoundedCornerShape(4.dp))
            ) {
            }
        }
    } else {
        val shape = RoundedCornerShape(4.dp)
        Box(
            modifier = modifier
                .shadow(16.dp, shape)
                .background(Color.White)
                .clip(shape)
        ) {
            Text(
                text = product.name.toString(),
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 24.dp)
            )
        }
    }
}