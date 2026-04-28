package com.example.nearmeet.ui.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nearmeet.ui.home.HomeScreen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object CreateEvent : Screen("create_event")
    object EventDetail : Screen("event_detail/{eventId}")
    object Profile : Screen("profile")
    object Login : Screen("login")
}

@Composable
fun NearMeetNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen()
        }
        composable(Screen.CreateEvent.route) {
            // CreateEventScreen()
        }
        composable(Screen.Profile.route) {
            // ProfileScreen()
        }
        composable(Screen.Login.route) {
            // LoginScreen()
        }
    }
}