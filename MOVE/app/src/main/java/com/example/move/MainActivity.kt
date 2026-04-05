package com.example.move


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import android.provider.Settings
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.move.ui.theme.MOVETheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MOVETheme {
                Energy()

            }
        }
    }


    @Composable
    fun Energy(){
        val context = LocalContext.current
        Column () {
            Button(onClick = {
                val intent = Intent(Settings.ACTION_SETTINGS)
                context.startActivity(intent)
            }) { Text("open settings") }
            Spacer(modifier = Modifier.height(10.dp))
            Button(onClick = {
                val intent1 = Intent(Settings.ACTION_WIFI_SETTINGS)
                context.startActivity(intent1)
            }) { Text("open wifi settings") }
            Spacer(modifier = Modifier.height(10.dp))
            Button(onClick = {
                val intent2 = Intent(Intent.ACTION_DIAL)
                context.startActivity(intent2)
            }) { Text(" dialer") }
            Spacer(modifier = Modifier.height(10.dp))
            Button(onClick = {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("https://wa.me/")
                context.startActivity(intent)
            }) { Text("open watsaap") }
            Spacer(modifier = Modifier.height(10.dp))
            Button(onClick = {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_TEXT, "HELLO THIS IS A MSSGE")
                context.startActivity(Intent.createChooser(intent, "share using"))
            }) { Text("send to") }
            Button(onClick = {
                val intent = Intent(Intent.ACTION_SENDTO)
                intent.data = Uri.parse("mailto:isha12@gmail.-com")
                intent.putExtra(Intent.EXTRA_SUBJECT, "WANT HOLIDAY")
                intent.putExtra(
                    Intent.EXTRA_TEXT,
                    "i am having  urgent work"
                )
                startActivity(intent)
            }) { Text("SEND EMAIL") }
        }}}


