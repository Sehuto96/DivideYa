package com.example.divideya.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParticipantListScreen(
    viewModel: com.example.divideya.ui.viewmodel.GroupViewModel,
    groupId: String,
    onBack: () -> Unit
) {
    val participants by viewModel.participants.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    LaunchedEffect(groupId) {
        viewModel.fetchParticipants(groupId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Participants") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
            Row(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.weight(1f)
                )
            }
            Button(
                onClick = {
                    if (name.isNotBlank()) {
                        viewModel.addParticipant(groupId, name, email)
                        name = ""
                        email = ""
                    }
                },
                modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth()
            ) {
                Text("Add participant")
            }

            Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                } else if (error != null) {
                    Text(text = "Error: $error", color = MaterialTheme.colorScheme.error, modifier = Modifier.align(Alignment.Center))
                } else if (participants.isEmpty()) {
                    Text(text = "No hay participantes aún", modifier = Modifier.align(Alignment.Center))
                } else {
                    LazyColumn {
                        items(participants) { participant ->
                            ListItem(
                                leadingContent = { Icon(Icons.Default.Person, contentDescription = null) },
                                headlineContent = { Text(participant.name) },
                                supportingContent = { participant.email?.let { Text(it) } },
                                trailingContent = {
                                    IconButton(onClick = {
                                        viewModel.removeParticipant(groupId, participant.id!!)
                                    }) {
                                        Icon(Icons.Default.Delete, contentDescription = "Remove")
                                    }
                                }
                            )
                            HorizontalDivider()
                        }
                    }
                }
            }
        }
    }
}