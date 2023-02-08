package com.example.composemvvm.models

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("name") val name: String? = null,
    @SerializedName("info") val info: String? = null
)