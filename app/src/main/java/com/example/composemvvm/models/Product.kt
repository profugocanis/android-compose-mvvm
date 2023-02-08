package com.example.composemvvm.models

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("name") val name: String? = null,
)