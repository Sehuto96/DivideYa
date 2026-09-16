package com.example.divideya.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Group
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.divideya.ui.viewmodel.GroupViewModel

@Composable
fun GroupListScreen(
    viewModel: GroupViewModel,
    onEditGroup: (String) -> Unit,
    onOpenParticipants: (String) -> Unit,
    onAddGroup: () -> Unit
) {
    val groups by viewModel.groups.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddGroup) {
                Icon(Icons.Default.Add, contentDescription = "Add Group")
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize()) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (error != null) {
                Text(text = "Error: $error", color = MaterialTheme.colorScheme.error, modifier = Modifier.align(Alignment.Center))
            } else if (groups.isEmpty()) {
                Text(text = "No hay grupos disponibles", modifier = Modifier.align(Alignment.Center))
            } else {
                LazyColumn {
                    items(groups) { group ->
                        ListItem(
                            leadingContent = {
                                Icon(Icons.Default.Group, contentDescription = null)
                            },
                            headlineContent = { Text(group.name) },
                            supportingContent = { group.description?.let { Text(it) } },
                            trailingContent = {
                                Row {
                                    IconButton(onClick = { onOpenParticipants(group.id!!) }) {
                                        Icon(Icons.Default.Group, contentDescription = "Participants")
                                    }
                                    IconButton(onClick = { onEditGroup(group.id!!) }) {
                                        Icon(Icons.Default.Edit, contentDescription = "Edit")
                                    }
                                    IconButton(onClick = { viewModel.deleteGroup(group.id!!) }) {
                                        Icon(Icons.Default.Delete, contentDescription = "Delete")
                                    }
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