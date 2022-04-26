package com.tukorea.project10

import android.app.Activity
import android.os.Bundle
import android.widget.Button

class SecondActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.second_activity)

        var btnReturn = findViewById<Button>(R.id.btnReturnSec)
        btnReturn.setOnClickListener {
            finish()
        }
    }
}