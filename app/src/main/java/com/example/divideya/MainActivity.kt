package com.example.divideya

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.divideya.ui.screens.ItemListScreen
import com.example.divideya.ui.screens.ItemUpsertScreen
import com.example.divideya.ui.screens.UserListScreen
import com.example.divideya.ui.screens.UserUpsertScreen
import com.example.divideya.ui.theme.DivideYaTheme
import com.example.divideya.ui.viewmodel.ItemViewModel
import com.example.divideya.ui.viewmodel.UserViewModel
import com.example.divideya.ui.viewmodel.GroupViewModel
import com.example.divideya.ui.screens.GroupListScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DivideYaTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val itemViewModel: ItemViewModel = viewModel()
    val userViewModel: UserViewModel = viewModel()
    val groupViewModel: GroupViewModel = viewModel()   // 👈 agregar esta línea

    Scaffold(
        // ... tu Scaffold actual, sin tocarlo
    ) { padding ->

        // 👇 TEMPORAL: reemplaza lo que sea que esté aquí por esto, solo para probar
        GroupListScreen(
            viewModel = groupViewModel,
            onEditGroup = { },
            onOpenParticipants = { },
            onAddGroup = { }
        )
    }
}