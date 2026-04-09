package com.example.unit1scroll

import android.os.Bundle
import android.widget.HorizontalScrollView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListPrefetchStrategy
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unit1scroll.ui.theme.Unit1ScrollTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
         // ScrollExample()
            //HorizontalScrollExaple()
            NestedScrollExample()
        }
    }
}

@Composable
fun  NestedScrollExample(){
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.fillMaxSize()
            .verticalScroll(scrollState).padding(16.dp)
    ) {
        Text(text =  "Top Section", fontSize = 24.sp,
            modifier = Modifier.padding(10.dp))

        LazyColumn(modifier = Modifier.height(300.dp)) {
            items(20){
                index-> Text(
                    text =  "Nested item $index",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
        Text(text = "Bottom Section",
            fontSize = 24.sp,
            modifier = Modifier.padding(10.dp))
    }
}
//fun HorizontalScrollExaple() {
//    val scrollState = rememberScrollState()
//Row(modifier = Modifier.fillMaxSize()
//    .horizontalScroll(scrollState).padding(16.dp)) {
//    for(i in 1..20){
//        Text(
//            text = "item $i",
//            fontSize =  20.sp,
//            modifier = Modifier.padding(8.dp)
//        )
//    }
//}
//}
//fun  ScrollExample() {
//    val scrollState = rememberScrollState()
//
//    Column(modifier = Modifier.fillMaxSize()
//        .verticalScroll(scrollState)
//        .padding(16.dp)
//    ) {
//        for(i in 1..90){
//            Text(
//                text =  "item $i",
//                fontSize = 22.sp,
//                modifier = Modifier.padding(10.dp)
//            )
//        }
//    }
//}
