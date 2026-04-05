//package com.example.codewithsnakebar
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.unit.dp
//import kotlinx.coroutines.launch
//
//@Composable
//fun ScreenContent(
//    paddingValues: PaddingValues,
//    snackbarHostState: SnackbarHostState
//) {
//    val scope = rememberCoroutineScope()
//
//    Column(
//        modifier = Modifier
//            .padding(paddingValues)
//            .padding(16.dp)
//    ) {
//
//        Text(
//            text = stringResource(R.string.welcome),
//            style = MaterialTheme.typography.headlineMedium
//        )
//
//        Spacer(modifier = Modifier.height(20.dp))
//
//        Button(onClick = {
//            scope.launch {
//                snackbarHostState.showSnackbar(
//                    message = "Login Successful",
//                    actionLabel = "OK"
//                )
//            }
//        }) {
//            Text(text = stringResource(R.string.show_snackbar))
//        }
//    }
//}
