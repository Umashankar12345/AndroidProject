package com.example.styling



import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp




class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Box(modifier = Modifier
                 .fillMaxSize()
    .background(Color(0xFF18D920))){
                        Text(text = buildAnnotatedString {
                            withStyle(
                            style = SpanStyle(
                                color = Color.Green,
                                fontSize = 50.sp

                            )
                        ){
                            append("J")
                        }
                            append("etPack")
                            withStyle(
                                style = SpanStyle(
                                    color = Color.Green,
                                    fontSize = 50.sp

                                )
                            ){
                                append("C")
                            }
                            append("ompose")
                        } ,
                            modifier = Modifier.padding(16.dp),
                            color =  Color.White,
                            fontSize = 30.sp,
               fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic,
textAlign = TextAlign.Center, textDecoration = TextDecoration.Underline )
}}}}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier,
color = Color.White
)
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Box(modifier = Modifier.background(Color(0xE2C51212))){
        Text(text = "JetPack Compose")
        //Greeting("Android")
    }
}