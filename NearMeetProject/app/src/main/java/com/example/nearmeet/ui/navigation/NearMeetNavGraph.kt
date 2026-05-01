package com.example.nearmeet.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.nearmeet.ui.auth.LoginScreen
import com.example.nearmeet.ui.createevent.CreateEventScreen
import com.example.nearmeet.ui.eventdetail.EventDetailScreen
import com.example.nearmeet.ui.home.HomeScreen
import com.google.firebase.auth.FirebaseAuth

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object CreateEvent : Screen("create_event")
    object EventDetail : Screen("event_detail/{eventId}") {
        fun createRoute(eventId: String) = "event_detail/$eventId"
    }
    object Profile : Screen("profile")
    object Login : Screen("login")
}

@Composable
fun NearMeetNavGraph(
    navController: NavHostController = rememberNavController()
) {
    val currentUser = FirebaseAuth.getInstance().currentUser
    val startDestination = if (currentUser != null) Screen.Home.route else Screen.Login.route

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.Home.route) {
            HomeScreen(
                onCreateEvent = { navController.navigate(Screen.CreateEvent.route) },
                onEventDetail = { eventId -> navController.navigate(Screen.EventDetail.createRoute(eventId)) },
                onProfile = { /* Navigate to profile */ }
            )
        }
        composable(Screen.CreateEvent.route) {
            CreateEventScreen(
                onBack = { navController.popBackStack() },
                onSuccess = { navController.popBackStack() }
            )
        }
        composable(
            route = Screen.EventDetail.route,
            arguments = listOf(navArgument("eventId") { type = NavType.StringType })
        ) {
            EventDetailScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}
