package com.tukorea.worldtime

import android.content.SharedPreferences
import android.content.res.Resources
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.LocaleList
import android.preference.Preference
import android.preference.PreferenceManager
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.view.get
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.slider.Slider
import com.tukorea.worldtime.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.bottom_sheet_layout.*
import kotlinx.android.synthetic.main.main_item.*
import kotlinx.android.synthetic.main.main_item.view.*
import org.joda.time.DateTimeUtils
import org.joda.time.DateTimeZone
import org.w3c.dom.Text
import java.sql.Time
import java.time.ZoneId
import java.time.format.TextStyle
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.prefs.Preferences
import java.util.stream.Stream
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var timeList : ListView
    lateinit var timeSlider : Slider
    lateinit var korTimeText : TextView
    lateinit var setLocationBtn : Button
    lateinit var zoneList : ListView
    private lateinit var sharedPreferences : SharedPreferences
    private lateinit var sharedPreferencesSelected : SharedPreferences
    private lateinit var editor : SharedPreferences.Editor
    private lateinit var editorSelected : SharedPreferences.Editor
    lateinit var searchBox : SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE)
        editor = sharedPreferences.edit() //시간대 저장할 저장소
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

        var timeSelect : Int
        var timeHour : Int
        var timeHourStr : String
        var timeMin : Int
        var timeMinStr : String
        var itemHourStr : String
        var itemMinStr : String


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
        var krOffSet = TimeUnit.MINUTES.convert(TimeZone.getTimeZone("ROK").rawOffset.toLong(), TimeUnit.MILLISECONDS).toString()

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






        var adapter : ArrayAdapter<String> = ArrayAdapter(this,
            R.layout.item_list, zoneDisplayList1)
        zoneList.adapter = adapter



        val items = mutableListOf<MainListItem>(
        )

        fun adapterUpdate() {
            items.clear()
            sharedPreferencesSelected.all.keys.map { item ->
                var tempId = sharedPreferences.getString(item, "")
                var tempOffset = TimeUnit.MINUTES.convert(TimeZone.getTimeZone(tempId).rawOffset.toLong(), TimeUnit.MILLISECONDS).toString()
                var timeDiff = krOffSet.toInt() - tempOffset.toInt()
                var timeDiffText = "${-timeDiff/60} 시간 ${timeDiff%60} 분" //아이템 시차
                var itemTime : Int //아이템 시간 정제 전
                if(timeSlider.value.toInt() < timeDiff){
                    timeDiff -= timeSlider.value.toInt()
                    itemTime =  1440 - timeDiff
                }
                else{
                    itemTime = if(timeSlider.value.toInt() - timeDiff > 1440){
                        timeSlider.value.toInt() - timeDiff - 1440
                    } else {
                        timeSlider.value.toInt() - timeDiff
                    }
                }
                var itemHours = itemTime/60
                var itemMinutes = itemTime%60
                if(itemHours < 10){
                    itemHourStr = "0${itemHours}"
                }
                else{
                    itemHourStr = itemHours.toString()
                }
                if(itemMinutes < 10){
                    itemMinStr = "0${itemMinutes}"
                }
                else{
                    itemMinStr = itemMinutes.toString()
                }

                var itemTimeText = "${itemHourStr} : ${itemMinStr}" //아이템 시간



                items.add(MainListItem(timeDiffText, item, itemTimeText))

            }
        }



        fun oneItemAdd(item: String){
            var tempId = sharedPreferences.getString(item, "")
            var tempOffset = TimeUnit.MINUTES.convert(TimeZone.getTimeZone(tempId).rawOffset.toLong(), TimeUnit.MILLISECONDS).toString()
            var timeDiff = krOffSet.toInt() - tempOffset.toInt()
            var timeDiffText = "${-timeDiff/60} 시간 ${timeDiff%60} 분" //아이템 시차
            var itemTime : Int //아이템 시간 정제 전
            if(timeSlider.value.toInt() < timeDiff){
                timeDiff -= timeSlider.value.toInt()
                itemTime =  1440 - timeDiff
            }
            else{
                itemTime = if(timeSlider.value.toInt() - timeDiff > 1440){
                    timeSlider.value.toInt() - timeDiff - 1440
                } else {
                    timeSlider.value.toInt() - timeDiff
                }
            }
            var itemHours = itemTime/60
            var itemMinutes = itemTime%60
            if(itemHours < 10){
                itemHourStr = "0${itemHours}"
            }
            else{
                itemHourStr = itemHours.toString()
            }
            if(itemMinutes < 10){
                itemMinStr = "0${itemMinutes}"
            }
            else{
                itemMinStr = itemMinutes.toString()
            }

            var itemTimeText = "${itemHourStr} : ${itemMinStr}" //아이템 시간



            items.add(MainListItem(timeDiffText, item, itemTimeText))

        }

        adapterUpdate()




        var adapter2 = MainListAdapter(this, items)
        timeList.adapter = adapter2








        zoneList.setOnItemClickListener { parent, view, position, id ->
            if (sharedPreferencesSelected.contains(adapter.getItem(position).toString())){
                Toast.makeText(applicationContext, "이미 등록되어있습니다.", Toast.LENGTH_SHORT).show()
                bottomSheetDialog.dismiss()
                return@setOnItemClickListener
            }
            editorSelected.putString(adapter.getItem(position).toString(), "선택")
            editorSelected.commit()
            oneItemAdd(adapter.getItem(position).toString())
            adapterUpdate()
            adapter2.notifyDataSetChanged()
            bottomSheetDialog.dismiss()

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
            if (adapter2.count >= 8){
                Toast.makeText(applicationContext, "최대 4개까지 등록 가능합니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            bottomSheetDialog.show()
        }

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
            adapterUpdate()
            adapter2.notifyDataSetChanged()
        }

        timeList.onItemLongClickListener = AdapterView.OnItemLongClickListener { _, _, index, _ ->
            var dlg = AlertDialog.Builder(this)
            var name = timeList[index].zoneName.text
            dlg.setTitle("삭제하시겠습니까?")
            dlg.setIcon(R.mipmap.ic_main)
            dlg.setPositiveButton("확인"){ dialog, which ->
                editorSelected.remove(name.toString())
                editorSelected.commit()
                adapterUpdate()
                adapter2.notifyDataSetChanged()
            }
            dlg.setNegativeButton("취소", null)
            dlg.show()
            true
        }




    }
}