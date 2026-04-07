package com.example.unit1lazyrow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.modifier.ModifierLocalReadScope
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unit1lazyrow.ui.theme.Unit1LazyRowTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Unit1LazyRowTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        //SimpleExample()
                        //LazyRowExample()
                        LazyColumn()
                    }
                }
            }
        }
    }
}

//@Composable
//fun SimpleExample() {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(16.dp),
//        horizontalArrangement = Arrangement.SpaceEvenly
//    ) {
//        Text("Android", fontSize = 18.sp)
//        Text("Jetpack", fontSize = 18.sp)
//        Text("Compose", fontSize = 18.sp)
//    }
//}
//
//@Composable // Added missing annotation
//fun LazyRowExample() {
//    val subjects = listOf(
//        "Android",
//        "Jetpack Compose",
//        "Kotlin",
//        "Coroutines",
//        "Jetpack"
//    )
//    LazyRow(
//        modifier = Modifier.padding(16.dp)
//    ) {
//        // items requires the import: androidx.compose.foundation.lazy.items
//        items(subjects) { subject ->
//            Card(
//                modifier = Modifier.padding(end = 10.dp),
//                // Fixed the double equals "==" error here
//                colors = CardDefaults.cardColors(
//                    containerColor = MaterialTheme.colorScheme.primaryContainer
//                )
//            ) {
//                Text(
//                    text = subject,
//                    modifier = Modifier.padding(12.dp)
//                )
//            }
            @Composable
            fun LazyColumn(){
                val students = listOf(
                    "Rahul sharma",
                     "Priya sharma",
                    "Aman Gupta",
                    "Sneha Patel",
                    "Umashankar sharma",
                    "Chhoti sharma"
                )
            LazyColumn(
                modifier = Modifier.padding(200.dp),
                contentPadding = PaddingValues(16.dp)
            ){
                items(students){
                    student ->
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(vertical =  8.dp),
                        elevation = CardDefaults.cardElevation(6.dp)
                    ) {
                        Text(
                            text =  student,
                            modifier = Modifier.padding(vertical = 8.dp))
                           // elevation = cardDefaults.cardElevation(6.dp)
//                        ){
//                             Text(text =  student,
//                                 modifier = Modifier.padding(16.dp),
//                                 fontSize = 18.sp)
                      }
                    }
                }
            }
