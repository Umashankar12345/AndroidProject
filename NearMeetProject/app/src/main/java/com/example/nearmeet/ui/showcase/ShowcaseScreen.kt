package com.example.nearmeet.ui.showcase

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class FeatureItem(
    val title: String,
    val description: String,
    val tech: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowcaseScreen(onBack: () -> Unit) {
    val features = listOf(
        FeatureItem("Dual view toggle", "Map + list switch via bottom nav", "Scaffold + State"),
        FeatureItem("Google Maps + markers", "Category-colored pins, tap to expand", "Maps Compose"),
        FeatureItem("Heatmap overlay", "HeatmapTileProvider via FAB toggle", "Maps Utils"),
        FeatureItem("GPS centering", "LaunchedEffect + cameraPositionState", "Play Services"),
        FeatureItem("Category filter chips", "LazyRow — Music, Sports, Art...", "FlowRow / Chips"),
        FeatureItem("Search bar", "Material 3 SearchBar API", "M3 Components"),
        FeatureItem("Recommendation cards", "Horizontal \"Recommended for you\" list", "LazyRow"),
        FeatureItem("Firebase Auth + Firestore", "Pre-configured in Gradle", "Firebase SDK"),
        FeatureItem("Material 3 + Hilt", "Scaffold, DI, reactive StateFlow", "Dagger Hilt")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("App Showcase", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF121212))
            )
        },
        containerColor = Color(0xFF121212) // Dark background like the screenshot
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            Text(
                "ALREADY BUILT — CONFIRMED IN YOUR CODE",
                color = Color.Gray,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(features) { feature ->
                    FeatureCard(feature)
                }
            }
        }
    }
}

@Composable
fun FeatureCard(feature: FeatureItem) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E)),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.fillMaxWidth().height(160.dp)
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            // The Green accent bar on the left
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(4.dp)
                    .background(Color(0xFF2E7D32))
            )

            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = feature.title,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = feature.description,
                    color = Color.LightGray,
                    fontSize = 12.sp,
                    lineHeight = 16.sp
                )
                Spacer(modifier = Modifier.weight(1f))
                
                // Tech/Subtitle
                Text(
                    text = feature.tech,
                    color = Color.Gray,
                    fontSize = 11.sp
                )
                
                Spacer(modifier = Modifier.height(8.dp))

                // The "Confirmed" Badge
                Surface(
                    color = Color(0xFFD1E7DD),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = "Confirmed",
                        color = Color(0xFF0A3622),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}
