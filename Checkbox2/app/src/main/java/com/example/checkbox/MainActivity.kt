package com.example.checkbox
 // ✅ package name fixed (no dot at start, lowercase)

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import  androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

                ProfileTaskScreen()
            }
        }
    }

@Preview
@Composable
fun ProfileTaskScreen() {

    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {

        // 🔹 Background Image
        Image(
            painter = painterResource(id = R.drawable.myimage),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            contentScale = ContentScale.Crop
        )

        // 🔹 Dark overlay
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .background(Color.Black.copy(alpha = 0.4f))
        )

        // 🔹 Welcome Text
        Text(
            text = "Welcome to Android UI",
            fontSize = 22.sp,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 40.dp)
        )

        // 🔹 Profile Card
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(20.dp)
                .background(Color.White, RoundedCornerShape(20.dp))
                .padding(20.dp)
        ) {

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
 
                Image(
                    painter = painterResource(id = R.drawable.myimage),
                    contentDescription = null,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                )



                Text(
                    text = "Aarav Sharma",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 10.dp)
                )

                Text(
                    text = "Student Developer",
                    fontSize = 14.sp,
                    color = Color.Gray
                )

                Row(modifier = Modifier.padding(top = 20.dp)) {

                    Button(onClick = {
                        Toast.makeText(context, "Followed", Toast.LENGTH_SHORT).show()
                    }) {
                        Text("Follow")
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Button(onClick = {
                        Toast.makeText(context, "Message Sent", Toast.LENGTH_SHORT).show()
                    }) {
                        Text("Message")
                    }
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun ProfilePreview() {
//    CHECKBOXXTheme {
//        ProfileTaskScreen()
//    }
//}

//
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.activity.enableEdgeToEdge
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.tooling.preview.Preview
//import com.example.checkbox.ui.theme.CheckboxTheme
//
//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContent {
//            CheckboxTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    CheckboxTheme {
//        Greeting("Android")
//    }
//}