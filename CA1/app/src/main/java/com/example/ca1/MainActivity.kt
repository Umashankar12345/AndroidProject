package com.example.ca1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Data model for restaurants
data class Restaurant(val name: String, val category: String)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                FoodScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodScreen() {
    // Sample Data
    val allRestaurants = listOf(
        Restaurant("Pizza Hut", "Veg"),
        Restaurant("Dominos", "Veg"),
        Restaurant("KFC", "Non-Veg"),
        Restaurant("Burger King", "Non-Veg"),
        Restaurant("Subway", "Veg")
    )

    var selectedCategory by remember { mutableStateOf("All") }
    var expanded by remember { mutableStateOf(false) }

    // Filtered list logic
    val filteredList = if (selectedCategory == "All") {
        allRestaurants
    } else {
        allRestaurants.filter { it.category == selectedCategory }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Food Delivery App") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            // 1. Category Selector (Dropdown/Spinner)
            Text("Select Category:", fontWeight = FontWeight.Bold)
            Box(modifier = Modifier.padding(vertical = 8.dp)) {
                OutlinedButton(onClick = { expanded = true }) {
                    Text(selectedCategory)
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    listOf("All", "Veg", "Non-Veg").forEach { category ->
                        DropdownMenuItem(
                            text = { Text(category) },
                            onClick = {
                                selectedCategory = category
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 2. Scrollable Restaurant List
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredList) { restaurant ->
                    RestaurantCard(restaurant)
                }
            }
        }
    }
}

@Composable
fun RestaurantCard(restaurant: Restaurant) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = restaurant.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = restaurant.category,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            // 3. Custom Rating Bar
            RatingBar()
        }
    }
}

@Composable
fun RatingBar() {
    var rating by remember { mutableIntStateOf(0) }

    Row {
        for (i in 1..5) {
            Text(
                text = if (i <= rating) "★" else "☆",
                fontSize = 24.sp,
                color = if (i <= rating) Color(0xFFFFB300) else Color.Gray,
                modifier = Modifier
                    .padding(2.dp)
                    .clickable { rating = i }
            )
        }
    }
}