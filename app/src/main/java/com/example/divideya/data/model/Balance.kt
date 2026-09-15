package com.example.divideya.data.model

data class Balance(
    val participantId: String,
    val participantName: String,
    val totalPaid: Double,
    val totalOwed: Double,
    val netBalance: Double
)
