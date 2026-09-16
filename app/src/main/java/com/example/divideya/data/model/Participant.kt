package com.example.divideya.data.model

import com.google.gson.annotations.SerializedName

data class Participant(
    @SerializedName("id") val id: String? = null,
    @SerializedName("groupId") val groupId: String,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String? = null,
    @SerializedName("avatar") val avatar: String? = null
)