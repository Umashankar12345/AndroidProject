package com.example.intent

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.intent.ui.theme.IntentTheme


class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IntentTheme {
                Energy()
            }
        }
    }

    @Composable
    fun Energy() {
        Box(modifier = Modifier.fillMaxSize()){
            Image(
                painter =  painterResource(id = R.drawable.Dhraj),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }
        Column(modifier = Modifier.padding(100.dp)) {
            Text("This is video Activity")
            Spacer(modifier = Modifier.height(30.dp))

            Button(onClick = { finish() }) {
                Text("Go Back")
            }
        }
    }
}
