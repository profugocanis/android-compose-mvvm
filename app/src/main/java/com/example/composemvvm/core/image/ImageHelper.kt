package com.example.composemvvm.core.image

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.appcompat.app.AppCompatActivity
import com.example.composemvvm.logget

class ImageHelper(activity: AppCompatActivity) {

    private var pickMedia: ActivityResultLauncher<PickVisualMediaRequest>? = null

    init {
        pickMedia = activity.registerForActivityResult(PickVisualMedia()) { uri ->
            logget(uri)
        }
    }

    fun select() {
        pickMedia?.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
    }
}