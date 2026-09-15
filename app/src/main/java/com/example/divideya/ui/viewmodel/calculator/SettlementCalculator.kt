package com.example.divideya.ui.viewmodel.calculator

import com.example.divideya.data.model.*

class SettlementCalculator {

    fun calculateSettlements(
        balances: List<Balance>
    ): List<Settlement> {

        val debtors =
            balances
                .filter {
                    it.netBalance < 0
                }
                .map {
                    Triple(
                        it.participantName,
                        -it.netBalance,
                        it.participantId
                    )
                }
                .toMutableList()

        val creditors =
            balances
                .filter {
                    it.netBalance > 0
                }
                .map {
                    Triple(
                        it.participantName,
                        it.netBalance,
                        it.participantId
                    )
                }
                .toMutableList()

        val result =
            mutableListOf<Settlement>()

        var debtorIndex = 0
        var creditorIndex = 0

        while (
            debtorIndex < debtors.size &&
            creditorIndex < creditors.size
        ) {

            val debtor =
                debtors[debtorIndex]

            val creditor =
                creditors[creditorIndex]

            val amount =
                minOf(
                    debtor.second,
                    creditor.second
                )

            result.add(
                Settlement(
                    debtorName = debtor.first,
                    creditorName = creditor.first,
                    amount = amount
                )
            )

            debtors[debtorIndex] =
                Triple(
                    debtor.first,
                    debtor.second - amount,
                    debtor.third
                )

            creditors[creditorIndex] =
                Triple(
                    creditor.first,
                    creditor.second - amount,
                    creditor.third
                )

            if (
                debtors[debtorIndex].second <= 0.01
            ) {
                debtorIndex++
            }

            if (
                creditors[creditorIndex].second <= 0.01
            ) {
                creditorIndex++
            }
        }

        return result
    }
}
