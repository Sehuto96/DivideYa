package com.example.divideya.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.divideya.data.model.*
import com.example.divideya.ui.viewmodel.calculator.*
import kotlinx.coroutines.flow.*

data class SummaryUiState(
    val totalExpenses: Double = 0.0,
    val balances: List<Balance> = emptyList(),
    val settlements: List<Settlement> = emptyList()
)

class SummaryViewModel : ViewModel() {

    private val balanceCalculator =
        BalanceCalculator()

    private val settlementCalculator =
        SettlementCalculator()

    private val _uiState =
        MutableStateFlow(
            SummaryUiState()
        )

    val uiState =
        _uiState.asStateFlow()

    fun calculateSummary(
        participants: List<Participant>,
        expenses: List<Expense>
    ) {

        val balances =
            balanceCalculator
                .calculateBalances(
                    participants,
                    expenses
                )

        val settlements =
            settlementCalculator
                .calculateSettlements(
                    balances
                )

        _uiState.value =
            SummaryUiState(
                totalExpenses = expenses.sumOf {
                    it.amount
                },
                balances = balances,
                settlements = settlements
            )
    }
}
