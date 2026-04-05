package com.example.progressbar

import android.R.attr.progress
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.progressbar.ui.theme.ProgressbarTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
//           CircularProgressBar()
//            LinearProgressBar()
            GradientProgressBar
//            CircularProgressWithValue()
        }
    }
}

//@Composable
//fun CircularProgressBar(){
//    Box(
//        contentAlignment = Alignment.Center,
//        modifier = Modifier.fillMaxSize()
//    ){
//        CircularProgressIndicator(
//            modifier = Modifier.size(160.dp),
//            strokeWidth = 10.dp
//        )
//    }
//}
@Composable
 fun LinearProgressBar(){
     var progress by remember {  mutableStateOf(0.0f)}

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center
    ) {
        LinearProgressIndicator(
            progress   =  progress,
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick =  {
            progress += 1.0f
            if(progress > 1f) progress = 0f
        }) {
            Text("Increase Progress")
        }
    }

 }
@Composable
fun GradientProgressBar() {

    val brush = Brush.horizontalGradient(
        colors = listOf(Color.Red, Color.Yellow, Color.Green)
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth(0.8f) // control width
                .height(12.dp)
                .background(Color.LightGray, shape = RoundedCornerShape(50))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.6f) // progress value
                    .fillMaxHeight()
                    .background(brush, shape = RoundedCornerShape(50))
            )
        }
    }
}
