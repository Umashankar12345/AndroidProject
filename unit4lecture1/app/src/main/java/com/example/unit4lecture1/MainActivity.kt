package com.example.unit4lecture1

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.unit4lecture1.ui.theme.Unit4lecture1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Unit4lecture1Theme {
                MyApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Material & Foundation Demo") }
            )
        }
    ) { paddingValues ->
        // Pass paddingValues to ensure content doesn't go under the TopAppBar
        MainScreen(paddingValues)
    }
}

@Composable
fun MainScreen(paddingValues: PaddingValues) {
    val context = LocalContext.current
    // Use 'var ... by remember' for easier syntax
    var name by remember { mutableStateOf("") }

    val itemsList = listOf(
        "Android", "Kotlin", "Jetpack Compose", "Material UI",
        "RecyclerView", "Retrofit", "Room DB", "Firebase"
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues) // Important: use Scaffold's padding
            .padding(16.dp)         // Additional inner padding
    ) {
        // Welcome Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Welcome student")
                Text("Demo App")
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // TextField
        item {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Enter Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Button
        item {
            Button(
                onClick = {
                    if (name.isNotEmpty()) {
                        Toast.makeText(context, "Hello, $name!", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Submit")
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // List with Cards
        items(itemsList) { item ->
            Card(
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .clickable {
                        Toast.makeText(context, "Clicked: $item", Toast.LENGTH_SHORT).show()
                    }
            ) {
                Text(
                    text = item,
                    modifier = Modifier.padding(16.dp) // Fixed parameter name to 'modifier'
                )
            }
        }
    }
}