package com.example.divideya.ui.screens
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.divideya.ui.viewmodel.SummaryViewModel

@Composable
fun SummaryScreen(
    viewModel: SummaryViewModel
) {

    val state by
    viewModel.uiState.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        item {

            Text(
                text =
                    "Total Gastado: $${state.totalExpenses}"
            )

            Spacer(
                modifier = Modifier.height(
                    16.dp
                )
            )

        }

        item {

            Text("Balances")

        }

        items(state.balances) {

            Text(
                "${it.participantName}: ${it.netBalance}"
            )

        }

        item {

            Spacer(
                modifier =
                    Modifier.height(16.dp)
            )

            Text(
                "Quién le debe a quién"
            )

        }

        items(state.settlements) {

            Text(
                "${it.debtorName} → ${it.creditorName}: ${it.amount}"
            )

        }
    }
}