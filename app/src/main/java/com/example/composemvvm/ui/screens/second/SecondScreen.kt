package com.example.composemvvm.ui.screens.second

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.example.composemvvm.core.BaseScreen
import com.example.composemvvm.core.NavigationArguments
import com.example.composemvvm.extentions.getObject
import com.example.composemvvm.models.Product
import org.koin.androidx.compose.koinViewModel

object SecondScreen : BaseScreen() {

    private const val PRODUCT_KEY = "PRODUCT_KEY"
    private const val PRODUCT_NAME_KEY = "PRODUCT_NAME_KEY"

    override val arguments = NavigationArguments()
        .registerString(PRODUCT_KEY)
        .registerString(PRODUCT_NAME_KEY)

    fun open(nav: NavController, product: Product) {
        val args = mapOf(
            PRODUCT_KEY to product,
            PRODUCT_NAME_KEY to (product.id ?: -1)
        )
        navigate(nav, args)
    }

    @Composable
    fun Screen(
        nav: NavController,
        stackEntry: NavBackStackEntry?,
        viewModel: SecondViewModel = koinViewModel()
    ) {

        val value = rememberSaveable() { mutableStateOf(0) }
        val product = stackEntry?.getObject<Product>(PRODUCT_KEY)
        val productId = stackEntry?.arguments?.getString(PRODUCT_NAME_KEY)?.toInt()

        Column {
            Text(text = "Product id: $productId")
            Text(text = "object: $product")
            Button(onClick = { nav.popBackStack() }) {
                Text(text = "Back")
            }
            Button(onClick = {
                value.value += 1
            }) {
                Text(text = "Incress")
            }
        }
    }
}