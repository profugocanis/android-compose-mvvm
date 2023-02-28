package com.example.composemvvm.models

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class Product(
    val uuid: String = UUID.randomUUID().toString(),
    @SerializedName("id") val id: Int? = null,
    @SerializedName("name") val name: String? = null,
) {

    val isNull: Boolean
        get() = id == null && name == null
}