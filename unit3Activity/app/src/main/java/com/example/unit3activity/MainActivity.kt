package com.example.unit3activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.unit3activity.ui.theme.Unit3ActivityTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Unit3ActivityTheme {
                        uma()
            }
        }
    }
    @Composable
    fun uma(){
        Column(modifier = Modifier.fillMaxSize().background(Color.Gray), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {

            Column(modifier = Modifier.fillMaxWidth((0.8f)).background(Color.Red)) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Row(modifier = Modifier.fillMaxWidth(0.5f).height(100.dp).border(color=Color.White, width = 2.dp)) { }
                    Row(modifier = Modifier.fillMaxWidth().height(100.dp).border(color=Color.White, width = 2.dp)) {}
                }
                Row(modifier = Modifier.fillMaxWidth()) {
                    Row(modifier = Modifier.fillMaxWidth(0.3f).height(100.dp).border(color=Color.White, width = 2.dp)) { }
                    Row(modifier = Modifier.fillMaxWidth(0.6f).height(100.dp).border(color=Color.White, width = 2.dp)) {}
                    Row(modifier = Modifier.fillMaxWidth().height(100.dp).border(color=Color.White, width = 2.dp)) {}
                }
                Row(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(0.5f).height(100.dp)
                            .border(color = Color.White, width = 2.dp)
                    ) { }
                    Row(
                        modifier = Modifier.fillMaxWidth().height(100.dp)
                            .border(color = Color.White, width = 2.dp)
                    ) {}
                }
            }
        }


    }
    }
