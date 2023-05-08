package com.example.composemvvm.utils

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material.icons.rounded.Info
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.lifecycle.setViewTreeViewModelStoreOwner
import androidx.savedstate.findViewTreeSavedStateRegistryOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import java.lang.ref.WeakReference

object CustomToast {

    private var currentToast: WeakReference<Toast>? = null

    fun showMessage(context: Context?, text: String?) {
        val view = createView(context, text, false) ?: return
        showViewInToast(view)
    }

    fun showError(context: Context?, text: String?) {
        val view = createView(context, text, true) ?: return
        showViewInToast(view)
    }

    private fun createView(context: Context?, text: String?, isError: Boolean): ComposeView? {
        text ?: return null
        if (context !is AppCompatActivity) return null
        return ComposeView(context).apply {
            setContent {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.Black.copy(alpha = 0.7f))
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        if (isError) Icons.Outlined.Warning else Icons.Rounded.Info,
                        contentDescription = null,
                        tint = if (isError) Color.Red.copy(alpha = 0.6f) else Color.White,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(text = text, color = Color.White)
                }
            }
            if (findViewTreeLifecycleOwner() == null) {
                setViewTreeLifecycleOwner(context)
            }
            if (findViewTreeViewModelStoreOwner() == null) {
                setViewTreeViewModelStoreOwner(context)
            }
            if (findViewTreeSavedStateRegistryOwner() == null) {
                setViewTreeSavedStateRegistryOwner(context)
            }
        }
    }

    private fun showViewInToast(view: View) {
        currentToast?.get()?.cancel()
        val toast = Toast(view.context)
        toast.setGravity(Gravity.BOTTOM, 0, 0)
        toast.duration = Toast.LENGTH_LONG
        toast.view = view
        toast.show()
        currentToast = WeakReference(toast)
    }
}