package com.example.divideya

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.divideya.ui.screens.ItemListScreen
import com.example.divideya.ui.screens.ItemUpsertScreen
import com.example.divideya.ui.screens.SummaryScreen
import com.example.divideya.ui.screens.UserListScreen
import com.example.divideya.ui.screens.UserUpsertScreen
import com.example.divideya.ui.theme.DivideYaTheme
import com.example.divideya.ui.viewmodel.ItemViewModel
import com.example.divideya.ui.viewmodel.UserViewModel

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

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                NavigationBarItem(
                    icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = null) },
                    label = { Text("Items") },
                    selected = currentDestination?.hierarchy?.any { it.route?.startsWith("items") == true } == true,
                    onClick = {
                        navController.navigate("items") {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = null) },
                    label = { Text("Users") },
                    selected = currentDestination?.hierarchy?.any { it.route?.startsWith("users") == true } == true,
                    onClick = {
                        navController.navigate("users") {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Assessment,
                            contentDescription = null
                        )
                    },
                    label = {
                        Text("Resumen")
                    },
                    selected = currentDestination?.hierarchy?.any { it.route?.startsWith("summary") == true } == true,
                    onClick = {
                        navController.navigate("summary") {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }

                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "items",
            modifier = Modifier.padding(innerPadding)
        ) {
            // Items Flow
            navigation(startDestination = "items_list", route = "items") {
                composable("items_list") {
                    ItemListScreen(
                        viewModel = itemViewModel,
                        onEditItem = { id -> navController.navigate("items_upsert?id=$id") },
                        onAddItem = { navController.navigate("items_upsert") }
                    )
                }
                composable(
                    route = "items_upsert?id={id}",
                    arguments = listOf(navArgument("id") {
                        type = NavType.StringType
                        nullable = true
                        defaultValue = null
                    })
                ) { backStackEntry ->
                    val id = backStackEntry.arguments?.getString("id")
                    ItemUpsertScreen(
                        viewModel = itemViewModel,
                        itemId = id,
                        onBack = { navController.popBackStack() }
                    )
                }
            }

            // Users Flow
            navigation(startDestination = "users_list", route = "users") {
                composable("users_list") {
                    UserListScreen(
                        viewModel = userViewModel,
                        onEditUser = { id -> navController.navigate("users_upsert?id=$id") },
                        onAddUser = { navController.navigate("users_upsert") }
                    )
                }
                composable(
                    route = "users_upsert?id={id}",
                    arguments = listOf(navArgument("id") {
                        type = NavType.StringType
                        nullable = true
                        defaultValue = null
                    })

                ) { backStackEntry ->
                    val id = backStackEntry.arguments?.getString("id")
                    UserUpsertScreen(
                        viewModel = userViewModel,
                        userId = id,
                        onBack = { navController.popBackStack() }
                    )
                }
            }
            navigation(
                startDestination = "summary_screen",
                route = "summary"
            ) {

                composable(
                    "summary_screen"
                ) {

                    SummaryScreen(
                        viewModel = viewModel()
                    )

                }

                }
            }
        }
    }
