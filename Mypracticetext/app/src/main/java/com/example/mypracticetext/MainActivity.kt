package com.example.mypracticetext

import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButtonElevation
import androidx.compose.material3.RadioButton


import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mypracticetext.ui.theme.MypracticetextTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MypracticetextTheme {

                    Greeting()
                }
            }
        }
    }


@Composable
fun Greeting(modifier: Modifier = Modifier) {
    var name by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var registration by remember { mutableStateOf("") }
    var checked by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("") }
    val textColor = if (selectedOption == "Male") Color.White else Color.Black

    val bgColor = if(selectedOption == "male") {
        Color.Black

    } else {
        Color.White
    }
    Column(modifier = Modifier
        .fillMaxSize()
        .background(bgColor)
        .padding(6.dp)) {
        TextField(
            name, { name = it }, modifier.padding(6.dp), label = { Text("Enter your name") }
        )
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Enter your passsword") },
            modifier = Modifier.padding(6.dp)
        )
        TextField(
            value = email,
            onValueChange = {email = it},
            label = {Text("Enter your password")},
            modifier = Modifier.padding( 6.dp)
        )
//        TextField(
//            name = registration,
//            onValueChange = { registration = it },
//            label = { ("Enter your registration ") },
//            modifier = Modifier.padding(5.dp),
//        )
        Box(modifier = Modifier.padding(9.dp))
        Row(modifier=Modifier.padding(9.dp)) {
            Checkbox(
                checked = checked,
                onCheckedChange = { checked = it },

            )
            Spacer(modifier = Modifier.size(9.dp))
            Text(text = "I agree to the terms and conditions")
        }

        Box(modifier = Modifier.padding(6.dp))

        Row(modifier = Modifier.padding(5.dp)) {
            Checkbox(
                checked = checked ,
                onCheckedChange = {checked = it}
            )
            Spacer(modifier = Modifier.padding(6.dp))
            Text(text =  "check previous  registation")
        }

            Row(modifier =Modifier.padding(12.dp)) {
                RadioButton(
                    selectedOption == "Male",
                    onClick = { selectedOption = "Male" }
                )

                    Spacer(modifier = Modifier.width(6.dp))
                        Text(text= "Male" , color = textColor)
                Spacer(modifier = Modifier.width(20.dp))

                    RadioButton(
                        selected = selectedOption = "Female",
                        onClick = { selectedOption = "Female" }
                    )
                    Text(
                        text = "Female" ,
                        colorResource() = if(selectedOption == "Male") Color.White else  Color.Black
                    )
                    }


                )

        }
    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MypracticetextTheme {
        Greeting()
    }
}