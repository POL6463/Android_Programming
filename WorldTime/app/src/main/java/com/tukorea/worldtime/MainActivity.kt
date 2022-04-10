package com.tukorea.worldtime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView

class MainActivity : AppCompatActivity() {
    lateinit var timeList : ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        timeList = findViewById<ListView>(R.id.timeList)
        var mid = arrayOf("히어로즈", "24시", "호스트", "글리", "프렌즈")

        var adapter : ArrayAdapter<String> = ArrayAdapter(this,
                    android.R.layout.simple_list_item_1, mid)
        timeList.adapter = adapter

    }
}