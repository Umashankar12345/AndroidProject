//package com.example.codewithsnakebar
//
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Home
//import androidx.compose.material.icons.filled.Person
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.res.stringResource
//import kotlinx.coroutines.launch
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun MainScreen() {
//
//    val snackbarHostState = remember { SnackbarHostState() }
//    val scope = rememberCoroutineScope()
//    var selectedIndex by remember { mutableStateOf(0) }
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text(text = stringResource(R.string.app_name)) }
//            )
//        },
//        snackbarHost = {
//            SnackbarHost(hostState = snackbarHostState)
//        },
//        bottomBar = {
//            NavigationBar {
//                NavigationBarItem(
//                    selected = selectedIndex == 0,
//                    onClick = { selectedIndex = 0 },
//                    label = { Text(stringResource(R.string.home)) },
//                    icon = { Icon(Icons.Default.Home, null) }
//                )
//                NavigationBarItem(
//                    selected = selectedIndex == 1,
//                    onClick = { selectedIndex = 1 },
//                    label = { Text(stringResource(R.string.profile)) },
//                    icon = { Icon(Icons.Default.Person, null) }
//                )
//            }
//        }
//    ) { paddingValues ->
//        ScreenContent(paddingValues, snackbarHostState)
//    }
//}
