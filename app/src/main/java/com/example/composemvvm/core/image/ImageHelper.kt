package com.example.composemvvm.core.image

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ImageHelper(activity: AppCompatActivity?) {

    private var pickMedia: ActivityResultLauncher<PickVisualMediaRequest>? = null
    private var onSelected: ((Bitmap?) -> Unit)? = null

    init {
        pickMedia = activity?.registerForActivityResult(PickVisualMedia()) { uri ->
            if (uri != null) {
                CoroutineScope(Dispatchers.IO).launch {
                    val bitmap = toBitmap(uri, activity)
                    onSelected?.invoke(bitmap)
                }
            }
        }
    }

    fun select(onSelected: (Bitmap?) -> Unit) {
        this.onSelected = onSelected
        pickMedia?.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
    }

    private fun toBitmap(uri: Uri, context: Context): Bitmap? {
        val imageStream = context.contentResolver.openInputStream(uri)
        return BitmapFactory.decodeStream(imageStream)
    }
}