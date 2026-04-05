
package com.example.navbar
//
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.Button
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.navigation.NavController
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import androidx.navigation.compose.rememberNavController
//
//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            AppNav()
//        }
//    }
//}
//
//@Composable
//fun AppNav() {
//    val nav = rememberNavController()
//
//    NavHost(
//        navController = nav,
//        startDestination = "home"
//    ) {
//        composable("home") { HomeScreen(nav) }
//        composable("detail") { DetailScreen(nav) }
//    }
//}
//
//@Composable
//fun HomeScreen(nav: NavController) {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(30.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Text(text = "Home Screen", modifier = Modifier.padding(bottom = 16.dp))
//        Button(onClick = { nav.navigate("detail") }) {
//            Text("Go to Details")
//        }
//    }
//}
//
//@Composable
//fun DetailScreen(nav: NavController) {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(30.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Text(text = "Detail Screen", modifier = Modifier.padding(bottom = 16.dp))
//        Button(onClick = { nav.popBackStack() }) {
//            Text("Back")
//        }
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun PreviewApp() {
//    AppNav()
//}

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.*
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNav()
        }
    }
}

@Composable
fun AppNav() {
    val nav = rememberNavController()

    NavHost(
        navController = nav,
        startDestination = "home",
        modifier = Modifier.fillMaxSize()
    ) {
        composable("home") { HomeScreen(nav) }
        composable ("inform"){getscreen(nav)}
       composable("detail") {loScreen(nav) }
        composable ("clean")

    }
}

@Composable
fun HomeScreen(nav: NavController) {


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = {nav.navigate("detail")}) {
            Text("Go to Details")
        }
        Button(onClick = {nav.navigate("inform")}) {
            Text("Go to Information")
        }

    }
}

@Composable
fun getscreen(nav: NavController ) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = {nav.popBackStack()}) {
            Text("Back")
        }
    }
}
@Composable
fun loScreen(nav: NavController){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {
        Button(onClick = {nav.popBackStack()}) {
            Text("scrren")
        }
    }
}
@Composable


@Preview(showBackground = true)
@Composable
fun PreviewApp() {
    AppNav()
}