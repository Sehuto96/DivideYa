package com.example.divideya.ui.viewmodel.calculator

import com.example.divideya.data.model.*

class BalanceCalculator {

    fun calculateBalances(
        participants: List<Participant>,
        expenses: List<Expense>
    ): List<Balance> {

        val paidMap = mutableMapOf<String, Double>()
        val owedMap = mutableMapOf<String, Double>()

        participants.forEach {

            paidMap[it.id] = 0.0
            owedMap[it.id] = 0.0

        }

        expenses.forEach { expense ->

            validateExpense(expense)

            paidMap[expense.payerId] =
                paidMap.getOrDefault(
                    expense.payerId,
                    0.0
                ) + expense.amount

            expense.shares.forEach { share ->

                owedMap[share.participantId] =
                    owedMap.getOrDefault(
                        share.participantId,
                        0.0
                    ) + share.amount
            }
        }

        return participants.map { participant ->

            val paid =
                paidMap[participant.id] ?: 0.0

            val owed =
                owedMap[participant.id] ?: 0.0

            Balance(
                participantId = participant.id,
                participantName = participant.name,
                totalPaid = paid,
                totalOwed = owed,
                netBalance = paid - owed
            )
        }
    }

    private fun validateExpense(
        expense: Expense
    ) {

        val sumShares =
            expense.shares.sumOf {
                it.amount
            }

        require(
            kotlin.math.abs(
                sumShares - expense.amount
            ) < 0.01
        ) {
            "La suma de participaciones debe coincidir con el valor total del gasto."
        }
    }
}