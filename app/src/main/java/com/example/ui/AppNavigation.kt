package com.example.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.data.AppDatabase
import com.example.data.TradeRepository
import com.example.data.UserStatsRepository

private val DashboardRoute = "dashboard"
private val JournalRoute = "journal"
private val AddTradeRoute = "add_trade"
private val CoachRoute = "coach"
private val LessonsRoute = "lessons"

@Composable
fun MainAppScreen(
    database: AppDatabase,
    userStatsRepository: UserStatsRepository
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route

    val tradeRepository = TradeRepository(database.tradeDao())
    val showBottomNav = currentDestination != AddTradeRoute

    Scaffold(
        bottomBar = {
            if (showBottomNav) {
                NavigationBar {
                    val isDashboard = currentDestination == DashboardRoute || currentDestination == null
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Home, contentDescription = "Dashboard") },
                        label = { Text("Dashboard") },
                        selected = isDashboard,
                        onClick = {
                            navController.navigate(DashboardRoute) {
                                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                    
                    val isLessons = currentDestination == LessonsRoute
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.List, contentDescription = "Lessons") },
                        label = { Text("Learn") },
                        selected = isLessons,
                        onClick = {
                            navController.navigate(LessonsRoute) {
                                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                    
                    val isJournal = currentDestination == JournalRoute
                    NavigationBarItem(
                        icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = "Journal") },
                        label = { Text("Journal") },
                        selected = isJournal,
                        onClick = {
                            navController.navigate(JournalRoute) {
                                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                    
                    val isCoach = currentDestination == CoachRoute
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.AutoAwesome, contentDescription = "Coach") }, 
                        label = { Text("AI Coach") },
                        selected = isCoach,
                        onClick = {
                            navController.navigate(CoachRoute) {
                                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = DashboardRoute,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(DashboardRoute) {
                DashboardScreen(
                    userStatsRepository = userStatsRepository,
                    tradeRepository = tradeRepository,
                    onNavigateToAddTrade = {
                        navController.navigate(AddTradeRoute)
                    }
                )
            }
            composable(LessonsRoute) {
                LessonsScreen(userStatsRepository = userStatsRepository)
            }
            composable(JournalRoute) {
                JournalScreen(
                    tradeRepository = tradeRepository,
                    onNavigateToAddTrade = {
                        navController.navigate(AddTradeRoute)
                    }
                )
            }
            composable(CoachRoute) {
                CoachScreen(tradeRepository = tradeRepository)
            }
            composable(AddTradeRoute) {
                AddTradeScreen(
                    tradeRepository = tradeRepository,
                    userStatsRepository = userStatsRepository,
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}
