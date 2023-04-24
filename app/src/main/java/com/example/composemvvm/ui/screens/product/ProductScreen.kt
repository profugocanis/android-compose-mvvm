package com.example.composemvvm.ui.screens.product

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.example.composemvvm.core.NavigationArguments
import com.example.composemvvm.core.ui.BaseScreen
import com.example.composemvvm.extentions.getInt
import com.example.composemvvm.extentions.getObject
import com.example.composemvvm.models.Product
import com.example.composemvvm.ui.dialogs.ProductBottomDialog
import org.koin.androidx.compose.koinViewModel

object ProductScreen : BaseScreen() {

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
        viewModel: ProductViewModel = koinViewModel()
    ) {

        val value = rememberSaveable() { mutableStateOf(0) }
        val product = stackEntry?.getObject<Product>(PRODUCT_KEY)
        val productId = stackEntry?.getInt(PRODUCT_NAME_KEY)
        val manager = (LocalContext.current as AppCompatActivity).supportFragmentManager

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Text(text = "Product id: $productId")
            Text(text = "${value.value}, object: $product")

            Button(onClick = {
                value.value += 1
            }) {
                Text(text = "Increase")
            }
            Button(onClick = {
                ProductBottomDialog.show(manager)
            }) {
                Text(text = "Dialog")
            }
        }
    }
}