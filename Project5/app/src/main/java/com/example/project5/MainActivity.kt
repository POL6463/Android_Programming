package com.example.project5

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        title="2018110006김민웅"
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        var params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT)

        var baseLayout = LinearLayout(this)
        baseLayout.orientation = LinearLayout.VERTICAL
        baseLayout.setBackgroundColor(Color.WHITE)
        setContentView(baseLayout, params)

        var edt = EditText(this)
        baseLayout.addView(edt)

        var btn = Button(this)
        btn.text = "버튼입니다"
        btn.setBackgroundColor(Color.YELLOW)
        baseLayout.addView(btn)

        var txt = TextView(this)
        txt.setTextColor(Color.MAGENTA)
        txt.setTextSize(20.toFloat())
        baseLayout.addView(txt)

        btn.setOnClickListener {
            txt.text = edt.text
        }


    }
}