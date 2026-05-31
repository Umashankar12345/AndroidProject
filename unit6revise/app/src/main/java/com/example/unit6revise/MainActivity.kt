package com.example.unit6revise

import android.R
import android.R.attr.top
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.unit6revise.ui.theme.Unit6reviseTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme{
                StudentProtalscreen()
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun   StudentProtalscreen(){
    val tabs = listOf(
        "Profile",
        "Attendance",
        "Results",
        "Fees"
    )
    val pagerState = rememberPagerState(
        initialPage =  0,
        pageCount = {tabs.size}
    )
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize()
        .padding(top = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TabRow(
            selectedTabIndex = pagerState.currentPage
        ) {
           tabs.forEachIndexed { index , title ->
               Tab(
                   selected = pagerState.currentPage == index,
                   onClick = {
                       scope.launch {
                           pagerState.animateScrollToPage(index)
                       }
                   },
                   text = {
                       Text(title)
                   }
               )
           }
            }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            when(page){
                0 -> studentProfile()
                1-> AttendanceScreen()
                2-> ResultScrren()
                3-> FeesScreen()
        }
        }
    }
}
@Composable

fun studentProfile(){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Column(
            horizontalAlignment   = Alignment.CenterHorizontally
        ) {
            Text(
                text =  "Student Profile" ,
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text("Name : Umashankar kumar")
            Text("Roll No : CSE205")
            Text("Branch : CSE")
        }
    }
}
@Composable

fun AttendanceScreen(){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text =  "Attendance",
                style =  MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text("Present : 82days")
            Text("Absent : 8 days")
            Text("Attendance Percentage : 82%")
        }
    }
}
@Composable
fun  ResultScrren(){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center

    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Results",
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text("Java : A")
            Text("Android : A")
            Text("DAA : A")
        }

    }

}
@Composable
fun FeesScreen(){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Fees",
                style = MaterialTheme.typography.headlineMedium
            )

                Spacer(modifier = Modifier.height(18.dp))

                Text("Total Fees : 20000")
                Text("Paid Fees : 10000")
                Text("Pending Fees : 10000")

        }

        }
}