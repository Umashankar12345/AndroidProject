package com.example.studentprofile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class StudentProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_profile)

        val logoutBtn = findViewById<Button>(R.id.btnLogout)

        logoutBtn.setOnClickListener {
            Toast.makeText(this, "Logout Successfully", Toast.LENGTH_SHORT).show()
        }
    }
}
