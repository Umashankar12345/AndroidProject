package com.example.unit5datastorerevise

import android.R
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.example.unit5datastorerevise.ui.theme.Unit5datastorereviseTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userperfences = userperfences(this)

        setContent {
            val biometricEnabled by userperfences.biometricsStatus.collectAsState(initial = false)

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = if (biometricEnabled) "Biometric Enabled" else "Biometric Disabled",
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(
                    modifier = Modifier.height(20.dp)
                )
                Switch(
                    checked = biometricEnabled,
                    onCheckedChange = { enable ->
                        lifecycleScope.launch {
                            userperfences.saveBiometricStatus(enable)
                        }
                    }
                )
            }
        }
    }
}