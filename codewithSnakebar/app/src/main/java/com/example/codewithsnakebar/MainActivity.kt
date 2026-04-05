package com.example.codewithsnakebar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.codewithsnakebar.ui.theme.CodewithSnakebarTheme



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CodewithSnakebarTheme {
                MainScreen()
            }
        }
    }
}
@Preview
@Composable
fun MainScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.umas),
            contentDescription = null,
            modifier = Modifier.fillMaxHeight(),
            contentScale = ContentScale.FillWidth
        )
        Column(
            modifier = Modifier.fillMaxSize().padding(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.home),
                fontWeight = FontWeight.Bold,
                fontSize =  18.sp,
                modifier = Modifier.padding(top  =  10.dp , bottom = 10.dp)
            )
                Spacer(modifier = Modifier.padding(10.dp))
            Text(
                text = stringResource(R.string.profile),
                fontWeight = FontWeight.Bold,
                fontSize =  18.sp,
                modifier = Modifier.padding(top  =  10.dp , bottom = 10.dp)
            )
        }
    }
}



//@Preview
//@Composable
//fun MainScreen() {
//    Box(modifier = Modifier.fillMaxSize()) {
//
//            Image(
//                painter = painterResource(R.drawable.umas),
//                contentDescription = null,
//                modifier = Modifier.fillMaxWidth(),
//                contentScale = ContentScale.FillWidth
//            )
//        Column(
//            modifier = Modifier.fillMaxSize().padding(),
//            verticalArrangement = Arrangement.Bottom,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//
//        Text(
//                text = stringResource(R.string.home),
//                fontWeight = FontWeight.Bold,
//            fontSize =  18.sp
////                modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
//            )
//            Spacer(modifier = Modifier.padding(10.dp))
//            Text(
//                text =  stringResource(R.string.profile),
//                fontWeight = FontWeight.Bold,
//                fontSize = 18.sp,
////                modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
//            )
//            Text(
//                text =stringResource(R.string.welcome),
//
//                fontWeight = FontWeight.Bold,
//                fontSize  = 20.sp,
//                color =  Color.Green
////                modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
//            )
//            Text(
//                text =  stringResource(R.string.User_city),
//
//                fontWeight = FontWeight.Bold,
//                fontSize = 20.sp,
//                color =  Color.Green
//
////                modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
//            )
//            Text(text = stringResource(R.string.smart_city),
//                fontWeight = FontWeight.Bold,
//                fontSize = 20.sp,
//                color = Color.Green
//
////                , modifier = Modifier.padding(bottom = 10.dp)
//          )
//        }
//    }
//}
