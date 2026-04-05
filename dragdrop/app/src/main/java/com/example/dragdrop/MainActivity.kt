package com.example.dragdrop

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

         val sub1 = findViewById<EditText>(R.id.Sub1)
        val sub2  = findViewById<EditText>(R.id.Sub2)
        val sub3 = findViewById<EditText>(R.id.Sub3)
        val button = findViewById<Button>(R.id.btnCalculate)

        button.setOnClickListener {
            val n1 = sub1.text.toString().toDoubleOrNull() ?: 0.0
            val  n2 = sub2.text.toString().toDoubleOrNull() ?: 0.0
            val n3 = sub3.text.toString().toDoubleOrNull()?: 0.0

            val avg = (n1 + n2 + n3) / 3
            val cgpa = avg / 10
            Toast.makeText(this, "your cgpa = %.2f".format(cgpa), Toast.LENGTH_SHORT).show()

        }

        }
    }
