package com.example.normal1application

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InternetPermissionDemo()
        }
    }
}

@Composable
fun InternetPermissionDemo() {

    var result by remember { mutableStateOf("Loading your public IP...") }

    LaunchedEffect(Unit) {
        result = withContext(Dispatchers.IO) {
            try {
                URL("https://api.ipify.org").readText()
            } catch (e: Exception) {
                "Error fetching IP"
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Your Public IP Address:", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = result, style = MaterialTheme.typography.bodyMedium)
    }
}