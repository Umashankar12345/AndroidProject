package com.example.nearmeet.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.nearmeet.ui.alerts.AlertsScreen
import com.example.nearmeet.ui.auth.LoginScreen
import com.example.nearmeet.ui.createevent.CreateEventScreen
import com.example.nearmeet.ui.eventdetail.EventDetailScreen
import com.example.nearmeet.ui.home.HomeScreen
import com.example.nearmeet.ui.profile.ProfileScreen
import com.example.nearmeet.ui.showcase.ShowcaseScreen
import com.google.firebase.auth.FirebaseAuth

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object CreateEvent : Screen("create_event")
    object EventDetail : Screen("event_detail/{eventId}") {
        fun createRoute(eventId: String) = "event_detail/$eventId"
    }
    object Profile : Screen("profile")
    object Login : Screen("login")
    object Alerts : Screen("alerts")
    object Showcase : Screen("showcase")
}

@Composable
fun NearMeetNavGraph(
    navController: NavHostController = rememberNavController()
) {
    // Determine start destination immediately based on existing session
    val startDestination = remember {
        if (FirebaseAuth.getInstance().currentUser != null) Screen.Home.route else Screen.Login.route
    }

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
                onProfile = { navController.navigate(Screen.Profile.route) },
                onAlerts = { navController.navigate(Screen.Alerts.route) }
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
        composable(Screen.Profile.route) {
            ProfileScreen(
                onBack = { navController.popBackStack() },
                onLogout = {
                    FirebaseAuth.getInstance().signOut()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                onEventClick = { eventId ->
                    navController.navigate(Screen.EventDetail.createRoute(eventId))
                },
                onShowcaseClick = {
                    navController.navigate(Screen.Showcase.route)
                }
            )
        }
        composable(Screen.Alerts.route) {
            AlertsScreen(
                onBack = { navController.popBackStack() },
                onEventClick = { eventId ->
                    navController.navigate(Screen.EventDetail.createRoute(eventId))
                }
            )
        }
        composable(Screen.Showcase.route) {
            ShowcaseScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}
