package com.example.composemvvm.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ProductBottomDialog : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent { ContentDialog() }
        }
    }

    @Composable
    private fun ContentDialog() {
        Column(
            modifier = Modifier
                .background(
                    Color.White,
                    shape = RoundedCornerShape(30, 30, 30, 30)
                )
        ) {
            Text("Dialog", modifier = Modifier.height(300.dp))
            Button(onClick = {
                this@ProductBottomDialog.dismiss()
            }) {
                Text(text = "Cancel")
            }
        }
    }

    companion object {

        fun show(manager: FragmentManager) {
            val dialog = ProductBottomDialog()
            dialog.isCancelable = true
            dialog.show(manager, dialog.tag)
        }
    }
}