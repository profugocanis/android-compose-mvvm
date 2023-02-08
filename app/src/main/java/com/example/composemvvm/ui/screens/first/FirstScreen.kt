package com.example.composemvvm.ui.screens.first

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.composemvvm.core.Source
import com.example.composemvvm.core.ui.BaseScreen
import com.example.composemvvm.extentions.showInfoDialog
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

            when (viewModel.products) {
                is Source.Error -> {
                    getContext().showInfoDialog((viewModel.products as Source.Error).exception.localizedMessage) {
                        viewModel.load()
                    }
                }
                is Source.Processing -> {
                    CircularProgressIndicator(modifier = Modifier.constrainAs(load) {
                        top.linkTo(parent.top, margin = 16.dp)
                        bottom.linkTo(parent.bottom, margin = 16.dp)
                        start.linkTo(parent.start, margin = 16.dp)
                        end.linkTo(parent.end, margin = 16.dp)
                    })
                }
                is Source.Success -> {
                    ProductList(nav, viewModel, modifier = Modifier.constrainAs(productList) {

                    })
                }
            }
        }
    }

    @Composable
    fun ProductList(nav: NavController, viewModel: FirstViewModel, modifier: Modifier) {
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
                                    SecondScreen.open(nav, product)
                                })
                    }
                }
            }
        }
    }
}