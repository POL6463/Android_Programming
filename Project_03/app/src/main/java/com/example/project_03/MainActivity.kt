package com.example.project_03

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    lateinit var editT : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title="2018110006김민웅"

        editT = findViewById<EditText>(R.id.EditT)
        editT.inputType = 0
        editT.setOnKeyListener { v, keyCode, event ->
            if(keyCode != null) {
                Toast.makeText(applicationContext, editT.text.toString(), Toast.LENGTH_SHORT).show()
            }
            false
        }

    }
}