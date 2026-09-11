package com.example.divideya.data.model

import com.google.gson.annotations.SerializedName

data class Expense(
    @SerializedName("id") val id: String? = null,
    @SerializedName("groupId") val groupId: String,
    @SerializedName("description") val description: String,
    @SerializedName("amount") val amount: Double,
    @SerializedName("paidBy") val paidBy: String,
    @SerializedName("category") val category: String,
    @SerializedName("date") val date: String
)