package com.example.unit6reviselibrarynavigationdrawer

import android.R
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RestrictTo
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.unit6reviselibrarynavigationdrawer.ui.theme.Unit6reviselibraryNavigationDrawerTheme
import kotlinx.coroutines.launch
import kotlinx.coroutines.selects.select
import kotlin.math.E

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme{
                LibraryDrawerApp()
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun   LibraryDrawerApp(){
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val  scope = rememberCoroutineScope()
    var selectedScreen by remember{ mutableStateOf("Books") }
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent =  {
            ModalDrawerSheet {
                Text(text = "Library Manger",
                    modifier = Modifier.padding(top = 47.dp),
                    style = MaterialTheme.typography.headlineSmall
                    )
                NavigationDrawerItem(
                    label = {Text("Books")},
                    selected = selectedScreen  == "Books",
                    onClick = {
                        selectedScreen  = "Books"
                        scope.launch {
                            drawerState.close()
                        }
                    }
                )
                NavigationDrawerItem(
                    label = {Text("Members")},
                    selected =  selectedScreen =="Members",
                    onClick = {
                        selectedScreen  = "Members"
                        scope.launch {
                            drawerState.close()
                        }
                    }
                )
                NavigationDrawerItem(
                    label =  {Text("Issue Records")},
                    selected = selectedScreen == "Issue Records",
                    onClick = {
                        scope.launch {
                            drawerState.close()
                        }
                    }
                )
                NavigationDrawerItem(
                    label  = {Text ("Settings")},
                    selected = selectedScreen == "Setting",
                    onClick = {
                        scope.launch {
                            drawerState.close()
                        }
                    }
                )

            }
        }
    ) {
        Scaffold(
            topBar =  {
                TopAppBar(
                    title = {Text("Library Mangement")},
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.open()
                            }
                        }
                        )
                        {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menu"
                            )
                        }
                    }

                )
            }

        ) {
            paddingValues ->
            Box(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                when(selectedScreen){
                    "Books" -> BookScreen()
                    "Members" -> MemberScreen()
                        "Issue Records" -> IssueRecordScreen()
                        "Setting" -> SettingScreen()
                }
            }
        }
    }
}
@Composable
fun BookScreen(){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text =  "Books",
            style =  MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(20.dp))

        Text("Android Studio")
        Text("java Programming")
        Text("Database Mangement")
        Text("cod")
    }
}
@Composable

fun MemberScreen(){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text   =  "Members",
            style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(30.dp))
        Text("Andrioid studio")
        Text("three members")
        Text("two members")
    }
}
@Composable

fun IssueRecordScreen() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "IssueRecordsScrren",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(30.dp))
        Text("fan issue")
            Text("wiring issue")
    }
}
@Composable
fun SettingScreen(){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Settings",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(30.dp))
        Text("seting Issue")
        Text("icon issue")
    }
}
