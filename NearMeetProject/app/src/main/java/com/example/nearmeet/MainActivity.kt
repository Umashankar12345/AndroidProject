package com.example.nearmeet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.nearmeet.ui.navigation.NearMeetNavGraph
import com.example.nearmeet.ui.theme.NearMeetTheme
import dagger.hilt.android.AndroidEntryPoint

import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapsSdkInitializedCallback

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        // Initialize Google Maps SDK
        MapsInitializer.initialize(
            applicationContext,
            MapsInitializer.Renderer.LATEST,
            OnMapsSdkInitializedCallback { renderer ->

                when (renderer) {

                    MapsInitializer.Renderer.LATEST -> {
                        println("Latest Maps Renderer Loaded")
                    }

                    MapsInitializer.Renderer.LEGACY -> {
                        println("Legacy Maps Renderer Loaded")
                    }
                }
            }
        )

        // Compose UI
        setContent {

            NearMeetTheme {

                NearMeetNavGraph()

            }
        }
    }
}