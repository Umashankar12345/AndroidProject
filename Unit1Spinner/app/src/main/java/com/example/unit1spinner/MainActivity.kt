package com.example.unit1spinner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue // Important for 'by' delegate
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // Using a Surface or Theme wrapper is good practice
            SpinnerExample()
        }
    }
}

@Composable
fun SpinnerExample() {
    val items = listOf("Select programming", "Android", "Kotlin", "Java")

    // Fixed naming: changed 'extended' to 'expanded'
    // Added 'setValue' import to allow 'var ... by'
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf(items[0]) }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(20.dp)
        .statusBarsPadding()) {

        Text(text = "Selected Language", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(10.dp))

        Box {
            Button(onClick = { expanded = true }) {
                Text(selectedItem)
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                // Fixed 'forEach' casing
                items.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(item) }, // Added required text parameter
                        onClick = {
                            selectedItem = item
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        // Fixed 'selectedItem' casing
        Text(text = "Selected: $selectedItem")
    }
}