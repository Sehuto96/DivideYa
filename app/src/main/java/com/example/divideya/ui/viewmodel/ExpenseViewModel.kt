package com.example.divideya.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.divideya.data.api.RetrofitClient
import com.example.divideya.data.model.Expense
import com.example.divideya.data.model.Split
import com.example.divideya.data.repository.ExpenseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode

class ExpenseViewModel : ViewModel() {
    private val repository = ExpenseRepository(RetrofitClient.apiService)

    private val _expenses = MutableStateFlow<List<Expense>>(emptyList())
    val expenses: StateFlow<List<Expense>> = _expenses

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private var currentGroupId: String? = null

    fun fetchExpenses(groupId: String) {
        currentGroupId = groupId
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _expenses.value = repository.getExpensesByGroup(groupId)
                _error.value = null
            } catch (e: Exception) {
                e.printStackTrace()
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun addExpenseEqualSplit(
        groupId: String,
        description: String,
        amount: Double,
        paidBy: String,
        category: String,
        date: String,
        participantIds: List<String>
    ) {
        if (participantIds.isEmpty()) {
            _error.value = "Debe haber al menos un participante para dividir el gasto"
            return
        }

        viewModelScope.launch {
            try {
                val expense = repository.createExpense(
                    Expense(
                        groupId = groupId,
                        description = description,
                        amount = amount,
                        paidBy = paidBy,
                        category = category,
                        date = date
                    )
                )

                val expenseId = expense.id
                    ?: throw IllegalStateException("El servidor no devolvió un id para el gasto")

                createEqualSplits(expenseId, amount, participantIds)

                fetchExpenses(groupId)
                _error.value = null
            } catch (e: Exception) {
                e.printStackTrace()
                _error.value = e.message
            }
        }
    }

    private suspend fun createEqualSplits(
        expenseId: String,
        amount: Double,
        participantIds: List<String>
    ) {
        val total = BigDecimal(amount.toString())
        val count = BigDecimal(participantIds.size)
        val baseShare = total.divide(count, 2, RoundingMode.DOWN)
        val assigned = baseShare.multiply(BigDecimal(participantIds.size - 1))
        val lastShare = total.subtract(assigned)

        participantIds.forEachIndexed { index, participantId ->
            val share = if (index == participantIds.lastIndex) lastShare else baseShare
            repository.createSplit(
                Split(
                    expenseId = expenseId,
                    participantId = participantId,
                    amount = share.toDouble()
                )
            )
        }
    }

    fun updateExpense(id: String, expense: Expense) {
        val groupId = currentGroupId ?: expense.groupId
        viewModelScope.launch {
            try {
                repository.updateExpense(id, expense)
                fetchExpenses(groupId)
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun deleteExpense(id: String, groupId: String) {
        viewModelScope.launch {
            try {
                val splits = repository.getSplitsByExpense(id)
                splits.forEach { split -> split.id?.let { repository.deleteSplit(it) } }
                repository.deleteExpense(id)
                fetchExpenses(groupId)
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun getSplitsForExpense(expenseId: String, onResult: (List<Split>) -> Unit) {
        viewModelScope.launch {
            try {
                onResult(repository.getSplitsByExpense(expenseId))
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}