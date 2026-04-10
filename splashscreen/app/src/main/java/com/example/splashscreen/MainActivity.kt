package com.example.splashscreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue    // IMPORTANT
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue    // IMPORTANT
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            splashscreen()
        }
    }
}

@Composable
fun splashscreen() {
    var showSplash by remember {mutableStateOf(true) }
    if(showSplash){
        splashscreen {showSplash = false}
    }else {
        HomeScreen()
    }
}
@Composable
fun splashscreen(onTimeout: () -> Unit){
    LaunchedEffect(Unit){
        delay(3000)
        onTimeout()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Welcome to My App",
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

@Composable
fun HomeScreen() {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Home Screen",
            style = MaterialTheme.typography.headlineMedium
        )
    }
}