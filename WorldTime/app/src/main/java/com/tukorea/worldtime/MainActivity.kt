package com.tukorea.worldtime

import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.LocaleList
import android.preference.Preference
import android.preference.PreferenceManager
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.android.material.slider.Slider
import org.joda.time.DateTimeUtils
import org.joda.time.DateTimeZone
import org.w3c.dom.Text
import java.sql.Time
import java.time.ZoneId
import java.time.format.TextStyle
import java.util.*
import java.util.prefs.Preferences
import java.util.stream.Stream
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var timeList : ListView
    lateinit var timeSlider : Slider
    lateinit var korTimeText : TextView
    lateinit var testText : TextView
    private lateinit var sharedPreferences : SharedPreferences
    private lateinit var editor : SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE)
        editor = sharedPreferences.edit()




        timeList = findViewById<ListView>(R.id.timeList)
        timeSlider = findViewById<Slider>(R.id.timeSlider)
        korTimeText = findViewById<TextView>(R.id.korTimeText)
        testText = findViewById<TextView>(R.id.testText)

        var timeSelect : Int
        var timeHour : Int
        var timeHourStr : String
        var timeMin : Int
        var timeMinStr : String
        var timeForm : String

        var locales : Array<out String> = Locale.getISOCountries()
        for (item in locales) {
        }



        var timeZNames1 = DateTimeZone.getAvailableIDs()
        var zoneDisplayList : ArrayList<String> = arrayListOf()
        timeZNames1.forEach { item ->
            zoneDisplayList.add(TimeZone.getTimeZone(item).getDisplayName(Locale.KOREA))
            editor.putString( TimeZone.getTimeZone(item).getDisplayName(Locale.KOREA) , item )}//한국어로 타임존 디스플레이 네임 출력
        editor.commit()
        var zoneDisplayList1 = zoneDisplayList.distinct().sorted() //중복제거, 정렬








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
            android.R.layout.simple_list_item_1, zoneDisplayList1)
        timeList.adapter = adapter


    }
}