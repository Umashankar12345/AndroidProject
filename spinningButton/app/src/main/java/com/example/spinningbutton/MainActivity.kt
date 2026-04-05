package com.example.spinningbutton

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme

import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

//                  SpinnerExample()
            SimpleDropdown()
        }
    }
}
//@Preview
//@Composable
//
//
//fun SpinnerExample() {
//
//    val items = listOf("Select Programming Language","Android", "Kotlin", "Java", "Python", "C++","Jetpack Compose")
//    var expanded by remember { mutableStateOf(false) }
//    var selectedItem by remember { mutableStateOf(items[0]) }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(20.dp)
//            .statusBarsPadding()
//    ) {
//
//        Text(text = "Select Language", style = MaterialTheme.typography.titleLarge)
//
//        Spacer(modifier = Modifier.height(10.dp))
//
//        Box {
//
//            Button(onClick = { expanded = true }) {
//                Text(selectedItem)
//            }
//
//            DropdownMenu(
//                expanded = expanded,
//                onDismissRequest = { expanded = false }
//            ) {
//
//                items.forEach { item ->
//                    DropdownMenuItem(
//                        text = { Text(item) },
//                        onClick = {
//                            selectedItem = item
//                            expanded = false
//                        }
//                    )
//                }
//            }
//        }
//
//        Spacer(modifier = Modifier.height(20.dp))
//
//        Text(text = "Selected: $selectedItem")
//    }
//}
//
//

//dropdown
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleDropdown() {

    val items = listOf("Android", "Kotlin", "Java", "Python", "C++")
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf(items[0]) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .statusBarsPadding()
    ) {

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {

            TextField(
                value = selectedItem,
                onValueChange = {},
                readOnly = true,
                label = { Text("Select Language") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded)
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {

                items.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(item) },
                        onClick = {
                            selectedItem = item
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}
