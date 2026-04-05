package com.example.checkbox



import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.darkColorScheme

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            checkBoxTheme {
                Energy()
            }
        }
    }
}

private fun MainActivity.checkBoxTheme(function: () -> Unit) {
    val todo = TODO("Not yet implemented")
}

@Preview
@Composable
fun Energy() {

    var name by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var gender by remember { mutableStateOf("male") }


    var football by remember { mutableStateOf(true) }
    var volleyball by remember { mutableStateOf(true) }
    var cricket by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            placeholder = { Text("Enter your name") }
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = { Text("Enter your password") }
        )

        Spacer(modifier = Modifier.height(10.dp))


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
        }

        Spacer(modifier = Modifier.height(20.dp))


        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Hobbies: ")

            Spacer(modifier = Modifier.width(10.dp))

            Checkbox(
                checked = football,
                onCheckedChange = { football = it }
            )
            Text("Football")

            Spacer(modifier = Modifier.width(10.dp))

            Checkbox(
                checked = volleyball,
                onCheckedChange = { volleyball = it }
            )
            Text("Volleyball")

            Spacer(modifier = Modifier.width(10.dp))

            Checkbox(
                checked = cricket,
                onCheckedChange = { cricket = it }
            )
            Text("Cricket")
        }
    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    checkBoxTheme{
        Energy()
    }
}