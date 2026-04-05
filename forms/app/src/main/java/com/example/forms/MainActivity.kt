package com.example.forms


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreen() {

    val context = LocalContext.current

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }
    var enableNotifications by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {

            Text(
                text = "Login",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Remember Me
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = rememberMe,
                        onCheckedChange = { rememberMe = it }
                    )
                    Text("Remember Me")
                }

                Switch(
                    checked = rememberMe,
                    onCheckedChange = { rememberMe = it }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Enable Notifications
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = enableNotifications,
                        onCheckedChange = { enableNotifications = it }
                    )
                    Text("Enable Notifications")
                }

                Switch(
                    checked = enableNotifications,
                    onCheckedChange = { enableNotifications = it }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(50),
                onClick = {

                    if (username.length < 4) {
                        Toast.makeText(
                            context,
                            "Username must be minimum 4 characters",
                            Toast.LENGTH_SHORT
                        ).show()

                    } else if (password.length < 6) {
                        Toast.makeText(
                            context,
                            "Password must be minimum 6 characters",
                            Toast.LENGTH_SHORT
                        ).show()

                    } else {
                        Toast.makeText(
                            context,
                            "Login Successful",
                            Toast.LENGTH_SHORT
                        ).show()

                        val intent = Intent(context, HomeActivity::class.java)
                        context.startActivity(intent)
                    }
                }
            ) {
                Text("Login")
            }

        }
    }
}

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Login Successful",
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun LoginPreview() {
//    LoginScreen()
//}


//  fun ProfileTaskScreen() {
//
//
//            Box(modifier = Modifier.fillMaxSize()) {
//
//                // 🔹 Background Image
//                Image(
//                    painter = painterResource(id = R.drawable.fees),
//                    contentDescription = null,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(250.dp),
//
//                    )
//
//                // 🔹 Black overlay over background
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(250.dp)
//                        .background(Color.Black.copy(alpha = 0.4f))
//                )
//
//                // 🔹 Welcome text on background
//                Text(
//                    text = "Welcome to Android UI",
//                    fontSize = 22.sp,
//                    color = Color.White,
//                    modifier = Modifier
//                        .align(Alignment.TopCenter)
//                        .padding(top = 40.dp)
//                )
//
//                // 🔹 Profile Card
//                Box(
//                    modifier = Modifier
//                        .align(Alignment.Center)
//                        .padding(20.dp)
//                        .background(Color.White, shape = RoundedCornerShape(20.dp))
//                        .padding(20.dp)
//                ) {
//
//                    Column(
//                        horizontalAlignment = Alignment.CenterHorizontally
//                    ) {
//
//                        // Profile Image
//                        Image(
//                            painter = painterResource(id = R.drawable.fees),
//                            contentDescription = null,
//                            modifier = Modifier
//                                .size(120.dp)
//                                .clip(CircleShape)
//                        )
//
//                        Text(
//                            text = "Aarav Sharma",
//                            fontSize = 20.sp,
//                            fontWeight = FontWeight.Bold,
//                            modifier = Modifier.padding(top = 10.dp)
//                        )
//
//                        Text(
//                            text = "Student Developer",
//                            fontSize = 14.sp,
//                            color = Color.Gray
//                        )
//
//                        Row(modifier = Modifier.padding(top = 20.dp)) {
//                            Button(onClick = { }) { Text("Follow") }
//
//                            Button(
//                                onClick = {},
//                                modifier = Modifier.padding(start = 10.dp)
//                            ) { Text("Message") }
//                        }
//                    }
//                }
//    }
//        Box(modifier =Modifier.size(400.dp).background(color = Color.Red), contentAlignment = Alignment.Center){
//            Text("hello world", modifier =
//                Modifier.align(Alignment.TopStart),
//                fontSize = 40.sp,
//                fontWeight = FontWeight.Bold
//            )
//            Image(
//                painter = painterResource(id= R.drawable.umas),
//                contentDescription = "star",
//                modifier = Modifier.matchParentSize()
//            )
////            Text("android" , modifier = Modifier.background(color = Color.Green),
////                fontSize = 50.sp, color = Color.Green)
//
//            Button(onClick = {println("hello world")}) {
//                Text("Login")
//            }
//        }
//    }
////        Box(
//            modifier = Modifier.size(200.dp)
//                .background(Color.LightGray),
//            contentAlignment = Alignment.Center
//        ){
//            Text("i am centered")
//            Text("i a top !",
//            modifier = Modifier.align(Alignment.TopEnd))
//
//            Text("I am above",
//                modifier = Modifier.align(Alignment.TopStart))
//        }
//    }
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp),
//            horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.CenterVertically
//
//        ) {
//            Text("left")
//            Text("medium")
//            Text("Right")
//        }
//    }
//        val context = LocalContext.current
//        var name by remember { mutableStateOf("") }
//        var password by remember { mutableStateOf("") }
//
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.spacedBy(8.dp)
//
//        ) {
//            Text("First item")
//            Text("Second item")
//            Text("Thirdd item")
//
//            Button(onClick = {
//                println("Button clicked !")
//            }) {
//                Text("fourth item")
//            }
//        }
//        }
//      Column(modifier = Modifier
//          .fillMaxSize()
//          .background(color = Color.Blue)
//          .padding(16.dp),
//          verticalArrangement = Arrangement.Top ,
//          horizontalAlignment = Alignment.CenterHorizontally) {
//          Text(text ="
//          OutlinedTextField(
//              value = name,
//              onValueChange = { },
//              label = { Text(" Enter your name") },
//
//          )
//          Spacer(modifier = Modifier.height(8.dp))
//          OutlinedTextField(
//              value = password,
//              onValueChange =  {},
//              label =  {Text(text ="Enter your  password")}
////          )
//         Row(
////             modifier = Modifier
////             .fillMaxSize()
////             .background(color = Color.Blue)
////             .padding(16.dp),
//             verticalAlignment = Alignment.CenterVertically ,
//             horizontalArrangement = Arrangement.spacedBy(8.dp)) {
//             Text(text ="Login Page")
//
//         }
//              OutlinedTextField(
//                  modifier = Modifier.weight(1f),
//                  value = name,
//                  onValueChange = {name = it},
//                  label =  {Text ( "Enter Password" ) }
//             )
//
//                     OutlinedTextField(
//                         modifier = Modifier.weight(1f),
//                        value = password,
//                         onValueChange =  {password = it},
//                         label =  {Text( "Enter your name")}
//                      )
//                  }
//
//
// Button(onClick = {
//    Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
//}) {
//    Text("Login")
// }
//  }
//   }
