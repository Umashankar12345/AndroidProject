package com.example.revisionunit5datastore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val  userPreferences = UserPrefernces(this)

        setContent{
            var name by remember { mutableStateOf("") }

            val savedName by userPreferences.getUserName.collectAsState(initial = "Loading")

            val darkMode by userPreferences.getDarkMode.collectAsState(initial = false)

            Column(modifier = Modifier.fillMaxSize().padding(20.dp), verticalArrangement = Arrangement.Center) {

                Text(
                    text =  "saved Name : $savedName",
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(value = name,
                    onValueChange = {
                        name = it
                    },

                    label = {
                        Text("Enter name")
                    })

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {

                        lifecycleScope.launch {

                            userPreferences.savedUsername(name)
                        }
                    }
                ) {

                    Text("Save Name")
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Dark Mode: $darkMode"
                )

                Spacer(modifier = Modifier.height(10.dp))

                Switch(
                    checked = darkMode,
                    onCheckedChange = {

                        lifecycleScope.launch {

                            userPreferences.saveDarkMode(it)
                        }
                    }
                )
            }
        }
    }
}