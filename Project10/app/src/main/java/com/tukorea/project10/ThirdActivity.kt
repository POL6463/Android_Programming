package com.tukorea.project10

import android.app.Activity
import android.os.Bundle
import android.widget.Button

class ThirdActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.third_activity)

        var btnReturn = findViewById<Button>(R.id.btnReturnTrd)
        btnReturn.setOnClickListener {
            finish()
        }
    }
}