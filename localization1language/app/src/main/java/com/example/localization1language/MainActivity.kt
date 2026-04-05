package com.example.localization1language

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.localization1language.ui.theme.Localization1languageTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Localization1()


        }
    }
}
@Preview(showBackground = true)
@Composable
fun Localization1() {
    Column(
        modifier = Modifier
            .fillMaxSize()          // takes full screen
            .statusBarsPadding(),  // avoids notch/status bar
        verticalArrangement = Arrangement.Center,   // vertical center
        horizontalAlignment = Alignment.CenterHorizontally // horizontal center
    ) {
        Text(text = stringResource(R.string.usr_name))
        Text(text = stringResource(R.string.usr_city))
    }
}
