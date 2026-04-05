package com.example.iplteams


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            IPLTeamsApp()
        }
    }
}

data class Team(
    val name: String,
    val city: String,
    val color: Color
)

val teams = listOf(
    Team("Chennai Super Kings", "Chennai", Color(0xFFFFC107)),
    Team("Mumbai Indians", "Mumbai", Color(0xFF1E88E5)),
    Team("Royal Challengers Bangalore", "Bangalore", Color(0xFFD32F2F)),
    Team("Kolkata Knight Riders", "Kolkata", Color(0xFF7B1FA2)),
    Team("Rajasthan Royals", "Jaipur", Color(0xFFEC407A)),
    Team("Delhi Capitals", "Delhi", Color(0xFF1976D2)),
    Team("Punjab Kings", "Punjab", Color(0xFFE53935)),
    Team("Sunrisers Hyderabad", "Hyderabad", Color(0xFFFF7043))
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IPLTeamsApp() {

    Scaffold(

        topBar = {

            CenterAlignedTopAppBar(
                title = {
                    Text("IPL Teams Info")
                }
            )

        }

    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
//
//            Text(
//                "Teams List View",
//                fontSize = 20.sp,
//                modifier = Modifier.padding(16.dp)
//            )
//
//            TeamListView()

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                "Teams Grid View",
                fontSize = 20.sp,
                modifier = Modifier.padding(16.dp)
            )

            TeamGridView()

        }

    }

}


//@Composable
//fun TeamListView() {
//
//    LazyColumn(
//        modifier = Modifier.fillMaxSize(),
//        contentPadding = PaddingValues(12.dp)
//    ) {
//
//        items(teams) { team ->
//
//            Card(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 8.dp),
//                colors = CardDefaults.cardColors(
//                    containerColor = team.color
//                ),
//                elevation = CardDefaults.cardElevation(6.dp)
//            ) {
//
//                Column(
//                    modifier = Modifier.padding(16.dp)
//                ) {
//
//                    Text(
//                        team.name,
//                        fontSize = 18.sp,
//                        color = Color.White
//                    )
//
//                    Text(
//                        team.city ,
//                        color = Color.White
//                    )
//
//                }
//
//            }
//
//        }
//
//    }
//
//}
//
@Composable
fun TeamGridView() {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(12.dp)
    ) {

        items(teams) { team ->

            Card(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = team.color
                ),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {

                Box(
                    modifier = Modifier
                        .height(120.dp)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            team.name,
                            color = Color.White,
                            fontSize = 16.sp
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            "Home City",
                            color = Color.White
                        )

                    }

                }

            }

        }

    }

}