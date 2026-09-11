package com.example.divideya.data.repository

import com.example.divideya.data.api.ApiService
import com.example.divideya.data.model.Expense
import com.example.divideya.data.model.Split

class ExpenseRepository(private val apiService: ApiService) {

    // Expense
    suspend fun getExpensesByGroup(groupId: String) = apiService.getExpensesByGroup(groupId)
    suspend fun getExpense(id: String) = apiService.getExpense(id)
    suspend fun createExpense(expense: Expense) = apiService.createExpense(expense)
    suspend fun updateExpense(id: String, expense: Expense) = apiService.updateExpense(id, expense)
    suspend fun deleteExpense(id: String) = apiService.deleteExpense(id)

    // Split
    suspend fun getSplitsByExpense(expenseId: String) = apiService.getSplitsByExpense(expenseId)
    suspend fun createSplit(split: Split) = apiService.createSplit(split)
    suspend fun updateSplit(id: String, split: Split) = apiService.updateSplit(id, split)
    suspend fun deleteSplit(id: String) = apiService.deleteSplit(id)
}