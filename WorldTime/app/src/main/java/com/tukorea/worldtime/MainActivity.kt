package com.tukorea.worldtime

import android.content.SharedPreferences
import android.content.res.Resources
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.LocaleList
import android.preference.Preference
import android.preference.PreferenceManager
import android.widget.*
import androidx.annotation.RequiresApi
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.slider.Slider
import kotlinx.android.synthetic.main.bottom_sheet_layout.*
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
    lateinit var setLocationBtn : Button
    lateinit var zoneList : ListView
    private lateinit var sharedPreferences : SharedPreferences
    private lateinit var sharedPreferencesSelected : SharedPreferences
    private lateinit var editor : SharedPreferences.Editor
    private lateinit var editorSelected : SharedPreferences.Editor
    lateinit var searchBox : SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE)
        editor = sharedPreferences.edit() //시간대 저장할 저장소

        sharedPreferencesSelected = getSharedPreferences("selected", MODE_PRIVATE)
        editorSelected = sharedPreferencesSelected.edit()


        val bottomSheetView = layoutInflater.inflate(R.layout.bottom_sheet_layout, null)
        val bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheetDialog.behavior.isFitToContents = false
        bottomSheetDialog.behavior.expandedOffset = 300
        bottomSheetDialog.behavior.isDraggable = false
        bottomSheetDialog.setContentView(bottomSheetView)


        setLocationBtn = findViewById<Button>(R.id.setLocationBtn)
        searchBox = bottomSheetView.findViewById<SearchView>(R.id.searchBox)
        zoneList = bottomSheetView.findViewById<ListView>(R.id.zoneList)
        timeList = findViewById<ListView>(R.id.timeList)
        timeSlider = findViewById<Slider>(R.id.timeSlider)
        korTimeText = findViewById<TextView>(R.id.korTimeText)
        testText = findViewById<TextView>(R.id.testText)

        var timeSelect : Int
        var timeHour : Int
        var timeHourStr : String
        var timeMin : Int
        var timeMinStr : String


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


        var adapter : ArrayAdapter<String> = ArrayAdapter(this,
            R.layout.item_list, zoneDisplayList1)
        zoneList.adapter = adapter


        var selectedTZ : ArrayList<String> = arrayListOf()


        sharedPreferencesSelected.all.keys.map { item ->
            selectedTZ.add(item.toString())
        }


        var adapter2 : ArrayAdapter<String> = ArrayAdapter(this,
            android.R.layout.simple_list_item_1, selectedTZ)
        timeList.adapter = adapter2


        zoneList.setOnItemClickListener { parent, view, position, id ->
            if (sharedPreferencesSelected.contains(adapter.getItem(position).toString())){
                Toast.makeText(applicationContext, "이미 등록되어있습니다.", Toast.LENGTH_SHORT).show()
                bottomSheetDialog.dismiss()
                return@setOnItemClickListener
            }
            selectedTZ.add(adapter.getItem(position).toString())
            editorSelected.putString(adapter.getItem(position).toString(), "선택")
            editorSelected.commit()
            testText.text = adapter.getItem(position).toString()
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.setOnDismissListener {
            adapter2 = ArrayAdapter(this,
                android.R.layout.simple_list_item_1, selectedTZ)
            timeList.adapter = adapter2
        }

        searchBox.setOnQueryTextListener(object :
        SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false


            }
        })

        setLocationBtn.setOnClickListener {
            if (adapter2.count >= 3){
                Toast.makeText(applicationContext, "최대 3개까지 등록 가능합니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            bottomSheetDialog.show()
        }






    }
}