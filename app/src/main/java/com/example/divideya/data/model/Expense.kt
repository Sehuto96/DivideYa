package com.example.divideya.data.model

data class Expense(
    val id: String,
    val description: String,
    val amount: Double,
    val payerId: String,
    val shares: List<ExpenseShare>
)