package com.example.lazyrowancolumn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lazyrowancolumn.ui.theme.LazyrowancolumnTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            LazyrowancolumnTheme {
                StudentDashboard()
            }
        }
    }
}
@Preview
@Composable
fun SimpleRowExample() {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        Text("Android", fontSize = 18.sp)
        Text("Kotlin", fontSize = 18.sp)
        Text("Compose", fontSize = 18.sp)

    }
}

@Composable
fun SimpleColumnExample() {

    Column(
        modifier = Modifier.padding(16.dp)
    ) {

        Text("Student 1", fontSize = 18.sp)
        Text("Student 2", fontSize = 18.sp)
        Text("Student 3", fontSize = 18.sp)

    }
}

@Composable
fun SubjectRow() {

    val subjects = listOf(
        "Math",
        "Science",
        "English",
        "History",
        "Computer",
        "Hindi"
    )

    LazyRow(
        modifier = Modifier.padding(16.dp)
    ) {

        items(subjects) { subject ->

            Card(
                modifier = Modifier.padding(end = 10.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {

                Text(
                    text = subject,
                    modifier = Modifier.padding(12.dp)
                )
            }
        }
    }
}

@Composable
fun StudentList() {

    val students = listOf(
        "Rahul Sharma",
        "Rohit Sharma",
        "Umashankar",
        "Sneha Patel",
        "Vishal Khemariya"
    )

    LazyColumn(
        modifier = Modifier.height(200.dp),
        contentPadding = PaddingValues(16.dp)
    ) {

        items(students) { student ->

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {

                Text(
                    text = student,
                    modifier = Modifier.padding(16.dp),
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Composable
fun CourseGrid() {

    val courses = listOf(
        "Android",
        "React Native",
        "Flutter",
        "Kotlin",
        "Java",
        "Python"
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.height(250.dp),
        contentPadding = PaddingValues(16.dp)
    ) {

        items(courses) { course ->

            Card(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {

                Box(
                    modifier = Modifier.padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {

                    Text(text = course)

                }
            }
        }
    }
}

@Composable
fun StudentDashboard() {

    LazyColumn {

        item {

            Text(
                text = "Row Example",
                fontSize = 22.sp,
                modifier = Modifier.padding(16.dp)
            )

            SimpleRowExample()

            Text(
                text = "Column Example",
                fontSize = 22.sp,
                modifier = Modifier.padding(16.dp)
            )

            SimpleColumnExample()

            Text(
                text = "LazyRow Example",
                fontSize = 22.sp,
                modifier = Modifier.padding(16.dp)
            )

            SubjectRow()

            Text(
                text = "LazyColumn Example",
                fontSize = 22.sp,
                modifier = Modifier.padding(16.dp)
            )

            StudentList()

            Text(
                text = "Grid Example",
                fontSize = 22.sp,
                modifier = Modifier.padding(16.dp)
            )

            CourseGrid()

        }
    }
}