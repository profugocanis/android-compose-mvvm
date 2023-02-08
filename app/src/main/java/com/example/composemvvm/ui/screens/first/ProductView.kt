package com.example.composemvvm.ui.screens.first

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.composemvvm.models.Product

@Composable
fun ProductView(product: Product, modifier: Modifier) {
    Card(modifier = modifier, elevation = 4.dp) {
        Text(
            text = product.name.toString(),
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 24.dp)
        )
    }
}