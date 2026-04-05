package com.example.makecomponents

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.Alignment
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.ui.text.font.FontWeight
import com.example.makecomponents.ui.theme.MakecomponentsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MakecomponentsTheme {

        }
    }
}
}
@Composable
fun AndroidUIComponentsScreen(){
    var expanded  by remember  {mutableStateOf(false)}

    var selectedCategory by remember  {mutableStateOf("Languages")}
    val categories = listOf("Languages" , "FrameWorks" , "Databases")

    val languages = listOf(
        "java" ,
        "kotlin" ,
        "python",
        "c++",
        "javascript"
    )
    val technologies = listOf(
        "Android",
        "Flutter" ,
        "React" ,
        "Firebase" ,
        "Mongodb",
        "NodeJS"
    )
    Column(
        modifier = Modifier.fillMaxSize() .padding(16.dp)

    ) {
        Text(
            text =  "Select Category",
            fontSize = 18.sp ,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))


        //Drop Menu

        Box{
            OutlinedButton(
                onClick = {expanded = true},
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(selectedCategory)
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {

                categories.forEach { category ->

                    DropdownMenuItem(
                        text = { Text(category) },
                        onClick = {
                            selectedCategory = category
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Programming Languages",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Language List
        languages.forEach { language ->

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text("•", fontSize = 20.sp)

                Spacer(modifier = Modifier.width(10.dp))

                Text(language, fontSize = 18.sp)
            }

            Divider()
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Technology Grid",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.height(200.dp)
        ) {

            items(technologies.size) { index ->

                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {

                    Column(
                        modifier = Modifier
                            .padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Icon(
                            imageVector = Icons.Default.Android,
                            contentDescription = "",
                            modifier = Modifier.size(40.dp)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(technologies[index])
                    }
                }
            }
        }
    }
}