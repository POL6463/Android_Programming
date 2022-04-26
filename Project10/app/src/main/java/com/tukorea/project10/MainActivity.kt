package com.tukorea.project10

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var secondBtn = findViewById<RadioButton>(R.id.secondBtn)
        var thirdBtn = findViewById<RadioButton>(R.id.thirdBtn)


        var btnNewActivity = findViewById<Button>(R.id.btnNewActivity)
        btnNewActivity.setOnClickListener {
            if (secondBtn.isChecked){
                var intent = Intent(applicationContext, SecondActivity::class.java)
                startActivity(intent)
            }
            if(thirdBtn.isChecked){
                var intent = Intent(applicationContext, ThirdActivity::class.java)
                startActivity(intent)
            }

        }
    }
}
