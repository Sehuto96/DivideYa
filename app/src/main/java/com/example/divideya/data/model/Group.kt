package com.example.divideya.data.model

import com.google.gson.annotations.SerializedName

data class Group(
    @SerializedName("id") val id: String? = null,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String? = null,
    @SerializedName("image") val image: String? = null,
    @SerializedName("createdAt") val createdAt: String? = null
)