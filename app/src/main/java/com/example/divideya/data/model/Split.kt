package com.example.divideya.data.model

import com.google.gson.annotations.SerializedName

data class Split(
    @SerializedName("id") val id: String? = null,
    @SerializedName("expenseId") val expenseId: String,
    @SerializedName("participantId") val participantId: String,
    @SerializedName("amount") val amount: Double
)