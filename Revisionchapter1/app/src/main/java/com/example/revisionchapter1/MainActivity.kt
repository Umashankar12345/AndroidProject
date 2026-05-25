package com.example.revisionchapter1

import android.os.Bundle
import android.text.Layout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.revisionchapter1.ui.theme.Revisionchapter1Theme
import org.intellij.lang.annotations.JdkConstants

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Revisionchapter1Theme {
//                fruit()
                counter()
            }
        }
    }
}
@Composable
fun counter(){
    var count by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text   =  "count: $count" , fontSize = 30.sp)
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Button(onClick = {
                count++;
            }) {
                Text("increase")
            }
            Button(onClick = {
                count--;
            }) {
                Text("decrease")
            }
        }
        }
}
//@Composable
//fun fruit(){
//    val items = listOf("uma" , "Dhiraj" , "chhoti" , "Anshu")
//    LazyColumn(
//        modifier = Modifier.fillMaxSize(),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
////        horizontalArragenment = Arrangement.Center,
////        verticalAlignment = Alignment.CenterVertically
//
//    ) {
//        items(items){
//            fruit->
//            Box(
//                modifier = Modifier.padding(12.dp)
//            )
//            Text(text   =  fruit ,
//                fontSize = 22.sp)
//        }
//    }
//}

//@Composable
//fun fruit(){
//    val items = listOf("Apple" ,"mango" , "banana" , "orange")
//
//    LazyRow(
//        modifier = Modifier.fillMaxSize(),
//        horizontalArrangement = Arrangement.Center,
//        verticalAlignment = Alignment.CenterVertically
//    ){
//        items(items){
//            fruit->
//            Box(
//                modifier = Modifier
//                .padding(15.dp),
//
//               //contentAlignment = Alignment.Center
//            ) {
//                Text(text = fruit
//                    , fontSize = 22.sp)
//            }
//        }
//
//
//}
//}