package com.example.page

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compoose.ui.theme.CompooseTheme



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CompooseTheme {
                Game()
            }
        }
    }@Preview
    @Composable
    fun Game(){
        Column( modifier = Modifier
            .fillMaxSize().background(color = Color.Yellow)
            .padding(16.dp), verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text ="Login page")
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = {Text(text = "enter your name")}
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = {Text(text = "password")}
            )
            Row (){ OutlinedTextField(
                value = "",
                onValueChange = {},
                label = {Text(text = "password")}
            )
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    label = {Text(text = "enter your name")}
                )}
            Button(onClick = { Toast.makeText(this@MainActivity, "login is sucessfull", Toast.LENGTH_SHORT).show()}) { Text(text = "login")}

        }
    }
}