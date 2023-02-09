package com.example.composemvvm.ui.screens.first

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.composemvvm.core.Source
import com.example.composemvvm.core.ui.BaseScreen
import com.example.composemvvm.extentions.showInfoDialog
import com.example.composemvvm.models.Product
import com.example.composemvvm.ui.dialogs.ProductBottomDialog
import com.example.composemvvm.ui.screens.second.SecondScreen
import org.koin.androidx.compose.koinViewModel

object FirstScreen : BaseScreen() {

    @Composable
    fun Screen(nav: NavController, viewModel: FirstViewModel = koinViewModel()) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
        ) {

            val (load, productList) = createRefs()

            val isLoading = remember { mutableStateOf(false) }

            HandleProduct(viewModel.products, isLoading)

            AnimatedVisibility(
                visible = !isLoading.value,
                enter = fadeIn(),
                exit = fadeOut(),
                modifier = Modifier.constrainAs(productList) {}
            ) {
                ProductList(nav, viewModel, Modifier)
            }

            AnimatedVisibility(
                visible = isLoading.value,
                enter = fadeIn(),
                exit = fadeOut(),
                modifier = Modifier.constrainAs(load) {
                    top.linkTo(parent.top, margin = 16.dp)
                    bottom.linkTo(parent.bottom, margin = 16.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                }
            ) {
//                Text(text = "loading...")
                CircularProgressIndicator(strokeWidth = 4.dp)
            }
        }
    }

    @Composable
    private fun ProductList(nav: NavController, viewModel: FirstViewModel, modifier: Modifier) {

        val manager = (getContext() as AppCompatActivity).supportFragmentManager

        LazyColumn(
            modifier = modifier
        ) {
            val products = viewModel.products
            if (products is Source.Success) {
                products.data?.forEach { product ->
                    item {
                        ProductView(
                            product,
                            modifier = Modifier
                                .fillParentMaxWidth()
                                .padding(PaddingValues(vertical = 4.dp, horizontal = 8.dp))
                                .clickable {
//                                    SecondScreen.open(nav, product)
                                    ProductBottomDialog.show(manager)
                                }
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun HandleProduct(source: Source<List<Product>>, isLoading: MutableState<Boolean>) {
        when (source) {
            is Source.Error -> {
                val message = source.exception.localizedMessage
                getContext().showInfoDialog(message) { }
                isLoading.value = false
            }
            is Source.Processing -> isLoading.value = true
            is Source.Success -> isLoading.value = false
        }
    }
}