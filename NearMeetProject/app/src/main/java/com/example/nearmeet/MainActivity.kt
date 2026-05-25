package com.example.nearmeet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.nearmeet.ui.navigation.NearMeetNavGraph
import com.example.nearmeet.ui.theme.NearMeetTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NearMeetTheme {
                NearMeetNavGraph()
            }
        }
    }
}
