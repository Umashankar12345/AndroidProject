package com.example.cycle

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        Log.d("hello", "onCreate: thisis running ")
        Toast.makeText(this, "oncreate", Toast.LENGTH_SHORT).show()


//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
   }

    override fun onStart() {
        super.onStart()

        Log.d("hello", "onStart:  this is running")
        Toast.makeText(this, "onStart", Toast.LENGTH_SHORT).show()

    }
    override fun onPause(){
        super .onPause()
        Log.d("hello", "onPause:  this is running")
        Toast.makeText(this, "OnStart", Toast.LENGTH_SHORT).show()
    }

}