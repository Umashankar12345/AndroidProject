package com.example.constrainlayout

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
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
        val username = findViewById<EditText>(R.id.etUsername)
        val password = findViewById<EditText>(R.id.etPassword)

        val loginBtn = findViewById<Button>(R.id.btnLogin)
        loginBtn.setOnClickListener {
            if (username.text.isEmpty() ||
                password.text.isEmpty()

            ) {
                Toast.makeText(this, "All fields are required",
                    Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Login Successful",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }
}
