package com.example.bottomappbar


import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.materialIcon
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BottomBarOnlyIcons()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomBarOnlyIcons() {

    val context = LocalContext.current
    var selectedIndex by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.DarkGray
            ) {
                NavigationIcon = {
                    IconButton(
                        onCLick = OnBackClick) {
                        icon(
                            imageVector = Icons.Default.ArrowBack, contenDescription = "BAck")

                    }
                },
                actions = {
                    IconButton(onClick = {
                    }) {
                        Icon(
                              imageVector = Icons.Default.Home,
                            contentDescription = "Home",
                            tint = Color.White
                        )
                    }
                    IconButton(onClick = {}) {
                        Icon(
                            imagevector = Icons.Default.search,
                            contentDescription = "search",
                            tint = Color.Blue
                        )
                    }
                    IconButton(onClick as  =  {}) {
                        Icon(
                            imageVector =  Icons.Default.person,
                            contextDescription = "profile",
                            tint = Color.White
                        )
                    }
                }

//
//                // Home Icon
//                NavigationBarItem(
//                    selected = selectedIndex == 0,
//                    onClick = {
//                        selectedIndex = 0
//                        Toast.makeText(context, "Home clicked", Toast.LENGTH_SHORT).show()
//                    },
//                    icon = {
//                        Icon(
//                            imageVector = Icons.Default.Home,
//                            contentDescription = "Home",
//                            tint = Color.White
//                        )
//                    }
//                )
//
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            Text(
                text = "App Details Screen",
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}
