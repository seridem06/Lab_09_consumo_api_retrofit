// Contenido esperado de D:\Lab09\app\src\main\kotlin\com\example\lab09\model\PostModel.kt

package com.example.lab09.model

import com.google.gson.annotations.SerializedName

data class PostModel(
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("body")
    val body: String
)