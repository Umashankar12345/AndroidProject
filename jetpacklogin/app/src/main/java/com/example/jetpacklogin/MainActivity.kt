package com.example.jetpacklogin

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpacklogin.ui.theme.JetpackloginTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetpackloginTheme {
                LoginScreen()
            }
        }
    }
}

@Composable
fun LoginScreen() {

    val context = LocalContext.current

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("male") }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.umashankar),  // ✔ valid drawable
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            alpha = 0.3f,
            contentScale = ContentScale.Crop
        )

        // Form UI
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text("Login Form", style = MaterialTheme.typography.headlineSmall)

            Spacer(modifier = Modifier.height(16.dp))

            // Username
            OutlinedTextField(
                value = username,
                onValueChange = {
                    username = it
                    Log.d("LoginForm", "Username: $username")
                },
                placeholder = { Text("Enter Username") }
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Password
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    Log.d("LoginForm", "Password: $password")
                },
                placeholder = { Text("Enter Password") }
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Email
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    Log.d("LoginForm", "Email: $email")
                },
                placeholder = { Text("Enter Email") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Gender Selection
            Row(verticalAlignment = Alignment.CenterVertically) {

                Text("Gender: ")

                RadioButton(
                    selected = gender == "male",
                    onClick = { gender = "male" }
                )
                Text("Male")

                Spacer(modifier = Modifier.width(10.dp))

                RadioButton(
                    selected = gender == "female",
                    onClick = { gender = "female" }
                )
                Text("Female")

                Spacer(modifier = Modifier.width(10.dp))

                RadioButton(
                    selected = gender == "other",
                    onClick = { gender = "other" }
                )
                Text("Other")
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Login Button
            Button(
                onClick = {
                    Toast.makeText(context, "Login Successful", Toast.LENGTH_LONG).show()
                    Log.d("LoginForm", "Login button clicked")
                }
            ) {
                Text("Login")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLogin() {
    JetpackloginTheme {
        LoginScreen()
    }
}
