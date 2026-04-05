import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FeesScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // FEES title
        Text(
            text = "FEES",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 60.dp, bottom = 40.dp)
        )package com.example.taskimpjet

        import android.os.Bundle
                import android.widget.Toast
                import androidx.activity.ComponentActivity
                import androidx.activity.compose.setContent
                import androidx.activity.enableEdgeToEdge
                import androidx.compose.foundation.Image
                import androidx.compose.foundation.background
                import androidx.compose.foundation.layout.Box
                import androidx.compose.foundation.layout.Column
                import androidx.compose.foundation.layout.Row
                import androidx.compose.foundation.layout.fillMaxSize
                import androidx.compose.foundation.layout.fillMaxWidth
                import androidx.compose.foundation.layout.height
                import androidx.compose.foundation.layout.padding
                import androidx.compose.foundation.layout.size
                import androidx.compose.foundation.shape.CircleShape
                import androidx.compose.foundation.shape.RoundedCornerShape
                import androidx.compose.material3.Button
                import androidx.compose.material3.Scaffold
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
                import androidx.compose.ui.unit.sp
                import com.example.taskimpjet.ui.theme.TasKImpjetTheme

        class MainActivity : ComponentActivity() {
            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                enableEdgeToEdge()
                setContent {
                    TasKImpjetTheme {
                        ProfileTaskScreen()
                    }
                }
            }
        }

        @Composable
        fun ProfileTaskScreen() {


            Box(modifier = Modifier.fillMaxSize()) {

                // 🔹 Background Image
                Image(
                    painter = painterResource(id = R.drawable.fees),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),

                    )

                // 🔹 Black overlay over background
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .background(Color.Black.copy(alpha = 0.4f))
                )

                // 🔹 Welcome text on background
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
                        .background(Color.White, shape = RoundedCornerShape(20.dp))
                        .padding(20.dp)
                ) {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        // Profile Image
                        Image(
                            painter = painterResource(id = R.drawable.fees),
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
                            Button(onClick = { }) { Text("Follow") }

                            Button(
                                onClick = {},
                                modifier = Modifier.padding(start = 10.dp)
                            ) { Text("Message") }
                        }
                    }
                }

                // 🔹 Footer Text

            }
        }

        // Logo
        Image(
            painter = painterResource(id = R.drawable.jetpack), // Replace with your logo
            contentDescription = "jetpack.jpg",
            modifier = Modifier
                .size(120.dp)
                .padding(vertical = 40.dp)
        )

        // Name with logo
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),
            contentAlignment = Alignment.Center
        ) {
            // Small logo before name
            Image(
                painter = painterResource(id = R.drawable.jetpack), // Replace with your logo
                contentDescription = "jetpack.jpg",
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.CenterStart)
            )

            Text(
                text = "Aaron Thomas",
                fontSize = 24.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(60.dp))

        // Buttons
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // First Button
            Button(
                onClick = { /* Handle button click */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue // Change to your preferred color
                )
            ) {
                Text(
                    text = "Button 1",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Second Button
            Button(
                onClick = { /* Handle button click */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Green // Change to your preferred color
                )
            ) {
                Text(
                    text = "Button 2",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FeesScreenPreview() {
    MaterialTheme {
        FeesScreen()
    }
}