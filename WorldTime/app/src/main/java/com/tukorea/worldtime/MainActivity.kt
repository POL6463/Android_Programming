package com.tukorea.worldtime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.slider.Slider
import java.sql.Time
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var timeList : ListView
    lateinit var timeSlider : Slider
    lateinit var korTimeText : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        timeList = findViewById<ListView>(R.id.timeList)
        timeSlider = findViewById<Slider>(R.id.timeSlider)
        korTimeText = findViewById<TextView>(R.id.korTimeText)

        var timeSelect : Int
        var timeHour : Int
        var timeHourStr : String
        var timeMin : Int
        var timeMinStr : String
        var timeForm : String
        var timeZoneList = TimeZone.getAvailableIDs()

        var currTime = Calendar.getInstance().time
        //현재시간 받아오기

        if(currTime.hours < 10){
            timeHourStr = "0${currTime.hours}"
        }
        else{
            timeHourStr = currTime.hours.toString()
        }
        if(currTime.minutes < 10){
            timeMinStr = "0${currTime.minutes}"
        }
        else{
            timeMinStr = currTime.minutes.toString()
        }

        korTimeText.setText("${timeHourStr} 시 ${timeMinStr} 분 ")
        //현재시간 받아와서 텍스트 초기화

        timeSlider.value = (currTime.hours.toInt() * 60 + currTime.minutes.toInt()).toFloat()
        //슬라이더 위치 현재시간으로 초기화





        timeSlider.addOnChangeListener { slider, value, fromUser ->
            timeSelect = value.toInt()
            timeHour = timeSelect / 60
            timeMin = timeSelect % 60
            if(timeHour < 10){
                timeHourStr = "0${timeHour}"
            }
            else{
                timeHourStr = timeHour.toString()
            }
            if(timeMin < 10){
                timeMinStr = "0${timeMin}"
            }
            else{
                timeMinStr = timeMin.toString()
            }

            korTimeText.setText("${timeHourStr} 시 ${timeMinStr} 분 ")
        }

        var mid = arrayOf("1번", "2번", "3반", "4번", "5번")

        var adapter : ArrayAdapter<String> = ArrayAdapter(this,
            android.R.layout.simple_list_item_1, timeZoneList)
        timeList.adapter = adapter


    }
}