package com.example.meowmory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.activity_finish.*

class FinishActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish)

        val button = findViewById<Button>(R.id.newGame)
        button.setOnClickListener {

            val intent = Intent(this, GameActivity::class.java)

            startActivity(intent)
        }
    }
}